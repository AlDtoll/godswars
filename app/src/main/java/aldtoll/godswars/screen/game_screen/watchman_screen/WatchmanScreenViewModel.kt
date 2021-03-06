package aldtoll.godswars.screen.game_screen.watchman_screen

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.executor.IEndTurnInteractor
import aldtoll.godswars.domain.logic.IActionPointsInteractor
import aldtoll.godswars.domain.logic.ISelectedPersonListInteractor
import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.StarShip
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.domain.storage.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function3
import java.util.*

class WatchmanScreenViewModel(
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor,
    private val playerTurnInteractor: IPlayerTurnInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val actionPointsInteractor: IActionPointsInteractor,
    private val watchmanInteractor: IWatchmanInteractor,
    private val selectedPersonInteractor: IPersonInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val endTurnInteractor: IEndTurnInteractor
) : IWatchmanScreenViewModel {

    private val playerName = App.getPref()?.getString("playerName", "")
    private val starShip = StarShip()

    override fun saveCellsLocal(cells: ArrayList<Cell>) {
        cellsListInteractor.update(cells)
    }

    override fun cellsData(): LiveData<MutableList<Cell>> {
        return LiveDataReactiveStreams.fromPublisher(
            cellsListInteractor.get().map {
                starShip.cells = ArrayList(it)
                it
            }.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun endTurn() {
        endTurnInteractor.execute()
    }

    override fun enableTurnButtonData(): LiveData<Boolean> {
        val observable = Observable.combineLatest(
            playerTurnInteractor.get(),
            placedInteractor.get(),
            cellsListInteractor.get(),
            Function3 { name: String, placed: Boolean, cells: MutableList<Cell> ->
                if (playerName != name) {
                    return@Function3 false
                } else {
                    if (!placed) {
                        cells.filter { it.type == Cell.Type.ENGINE }.size == 1 &&
                                cells.filter { it.type == Cell.Type.BRIDGE }.size == 1 &&
                                cells.filter { it.type == Cell.Type.REACTOR }.size == 1
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

    override fun isPlacedData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            placedInteractor.get()
                .toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun cellsPanelVisibilityData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            placedInteractor.get().map { !it }.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun turnButtonTextData(): LiveData<String> {
        val observable =
            placedInteractor.get().startWith(false).map {
                val watchman = watchmanInteractor.value()
                watchmanInteractor.update(watchman)
                databaseInteractor.saveWatchman()
                if (!it) {
                    "?????????????????? ????????????????????"
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
            arrivedInteractor.get().startWith(false)
        ) { name: String, placed: Boolean, arrived: Boolean ->
            if (name == playerName) {
                if (placed) {
                    "???????? ??????"
                } else {
                    "?????????? ???????????????????? ??????????????, ?????????????????? ?? ????????????"
                }
            } else {
                if (!arrived) {
                    "???????????????? ???????????????? ?????????? ?????? ??????????????????????????..."
                } else {
                    "???????????????? ????????????????..."
                }
            }
        }
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun increaseMaxActionPoints(points: Int) {
    }

    override fun decreaseMaxActionPoint(points: Int) {
    }

    override fun actionPointsData(): LiveData<MutableList<ActionPoint>> {
        return LiveDataReactiveStreams.fromPublisher(
            actionPointsInteractor.get().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun personsData(): LiveData<MutableList<Person>> {
        val observable = Observable.combineLatest(
            selectedPersonListInteractor.data(),
            arrivedInteractor.get()
        ) { guests: MutableList<Person>, arrived: Boolean ->
            mutableListOf<Person>()
        }
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun selectPerson(person: Person) {
        selectedPersonInteractor.update(person)
    }

    override fun selectedPersonData(): LiveData<Person> {
        return LiveDataReactiveStreams.fromPublisher(
            selectedPersonInteractor.get().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun clickCell(item: Cell) {
        cellsListInteractor.update(starShip.cells)
    }
}