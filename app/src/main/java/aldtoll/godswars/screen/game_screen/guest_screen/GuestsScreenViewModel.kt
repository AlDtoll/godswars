package aldtoll.godswars.screen.game_screen.guest_screen

import aldtoll.godswars.App
import aldtoll.godswars.domain.executor.IClickCellIntreactor
import aldtoll.godswars.domain.executor.IEndTurnInteractor
import aldtoll.godswars.domain.logic.IActionPointsInteractor
import aldtoll.godswars.domain.logic.ISelectedPersonInteractor
import aldtoll.godswars.domain.logic.ISelectedPersonListInteractor
import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.domain.storage.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function3

class GuestsScreenViewModel(
    private val cellsListInteractor: ICellsListInteractor,
    private val playerTurnInteractor: IPlayerTurnInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val actionPointsInteractor: IActionPointsInteractor,
    private val personInteractor: IPersonInteractor,
    private val selectedPersonInteractor: ISelectedPersonInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val endTurnInteractor: IEndTurnInteractor,
    private val cellInteractor: ICellInteractor,
    private val clickCellIntreactor: IClickCellIntreactor
) : IGuestsScreenViewModel {

    companion object {
        const val ACTION_POINTS_FOR_MOVE = 2
    }

    private val localPlayerName = App.getPref()?.getString("playerName", "")

    override fun saveCellsLocal(cells: ArrayList<Cell>) {
        cellsListInteractor.update(cells)
    }

    override fun cellsData(): LiveData<MutableList<Cell>> {
        return LiveDataReactiveStreams.fromPublisher(
            cellsListInteractor.get().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun endTurn() {
        endTurnInteractor.execute()
    }

    override fun enableTurnButtonData(): LiveData<Boolean> {
        val observable = Observable.combineLatest(
            playerTurnInteractor.get(),
            arrivedInteractor.get(),
            cellsListInteractor.get(),
            Function3 { name: String, arrived: Boolean, cells: MutableList<Cell> ->
                if (localPlayerName != name) {
                    return@Function3 false
                } else {
                    if (!arrived) {
                        val filter = cells.filter { it.type == Cell.Type.PIER }
                        val find = filter.find { it.persons?.isNotEmpty() ?: false }
                        find != null
                    } else {
                        true
                    }
                }
            }
        )
        return LiveDataReactiveStreams.fromPublisher(
            observable
                .toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun isArrivedData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            arrivedInteractor.get()
                .toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun turnButtonTextData(): LiveData<String> {
        val observable = arrivedInteractor.get().startWith(false).map {
            if (!it) {
                "????????????????????????????"
            } else {
                "?????????????????? ??????"
            }
        }
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun currentStatusTextData(): LiveData<String> {
        val observable = Observable.combineLatest(
            playerTurnInteractor.get(),
            placedInteractor.get().startWith(false),
            arrivedInteractor.get().startWith(false),
        ) { runningPlayerName: String, placed: Boolean, arrived: Boolean ->
            if (runningPlayerName == this.localPlayerName) {
                if (arrived) {
                    "???????? ??????"
                } else {
                    "?????????? ?????????????? ???????????????????? ????????"
                }
            } else {
                if (!placed) {
                    "?????????????????? ???????????? ???????? ??????????..."
                } else {
                    "???? ?????????????????? ????????????????????..."
                }
            }
        }
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun actionPointsData(): LiveData<MutableList<ActionPoint>> {
        return LiveDataReactiveStreams.fromPublisher(
            actionPointsInteractor.get().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun selectedPersonsData(): LiveData<List<Person>> {
        return LiveDataReactiveStreams.fromPublisher(
            selectedPersonListInteractor.data().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun clickPerson(person: Person) {
        personInteractor.update(person)
    }

    override fun selectedPersonData(): LiveData<Person> {
        return LiveDataReactiveStreams.fromPublisher(
            selectedPersonInteractor.data().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun clickCell(item: Cell) {
        cellInteractor.update(item)
        clickCellIntreactor.execute(item)

        if (!arrivedInteractor.value()) {
            if (item.type == Cell.Type.PIER) {
                val selectedGuests = selectedPersonListInteractor.value()
                if (item.persons == selectedGuests) {
                    item.persons = arrayListOf()
                    starShip.hide(item.position.toInt())
                } else {
                    val piers =
                        cellsListInteractor.value().filter { it.type == Cell.Type.PIER }
                    piers.forEach { pier ->
                        pier.persons = arrayListOf()
                        starShip.hide(pier.position.toInt())
                    }
                    item.persons = selectedGuests
                    starShip.show(item.position.toInt())
                }
            }
        } else {
            val selectedPerson = personInteractor.value()
            if (selectedPerson != null && selectedPerson != Person.nobody()) {
                if (canBeMoved(item.position.toInt(), selectedPerson)) {
                    movePerson(item)
                }
            } else {
                starShip.selectCell(item.position.toInt())
                selectPersons(item.persons!!)
            }
            starShip.hideAll()
            starShip.showGuests()
            starShip.disabledAll()
            if (selectedPerson != null && selectedPerson != Person.nobody()) {
                val cellsWithPersons =
                    starShip.cells.filter { !it.persons.isNullOrEmpty() }
                val previousCellWhereWasPerson = cellsWithPersons.find {
                    it.persons?.any { person -> person == selectedPerson } ?: false
                }
                previousCellWhereWasPerson?.run {
                    starShip.showEnabledForGuest(this.position.toInt())
                }
            }
        }
        cellsListInteractor.update(starShip.cells)
    }

    private fun movePerson(item: Cell) {
        val selectedPerson = personInteractor.value()
        val cellsWithPersons =
            starShip.cells.filter { !it.persons.isNullOrEmpty() }
        val previousCellWhereWasPerson = cellsWithPersons.find {
            it.persons?.any { person -> person == selectedPerson } ?: false
        }
        previousCellWhereWasPerson?.run {
            this.persons?.remove(selectedPerson)
        }

        val persons = starShip.cells[item.position.toInt()].persons
        updateActionPoints(selectedPerson!!)
        persons?.add(selectedPerson)
        starShip.cells[item.position.toInt()].persons = persons
    }

    private fun updateActionPoints(selectedPerson: Person) {
        val ap = selectedPerson.ap
        selectedPerson.ap = ap - ACTION_POINTS_FOR_MOVE
        clickPerson(selectedPerson)
    }

    private fun canBeMoved(position: Int, selectedPerson: Person): Boolean {
        if (selectedPerson.ap > ACTION_POINTS_FOR_MOVE) {
            return true
        }
        return false
    }
}