package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.unit.Guest
import aldtoll.godswars.domain.storage.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import io.reactivex.functions.Function5
import java.util.*

class GameScreenViewModel(
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor,
    private val guestNameInteractor: IGuestNameInteractor,
    private val playerTurnInteractor: IPlayerTurnInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val actionPointsInteractor: IActionPointsInteractor,
    private val watchmanInteractor: IWatchmanInteractor,
    private val guestListInteractor: IGuestListInteractor
) : IGameScreenViewModel {

    private val playerName = App.getPref()?.getString("playerName", "")

    override fun saveCellsLocal(cells: ArrayList<Cell>) {
        cellsListInteractor.update(cells)
    }

    override fun cellsData(): LiveData<MutableList<Cell>> {
        return LiveDataReactiveStreams.fromPublisher(
            cellsListInteractor.get().toFlowable(BackpressureStrategy.BUFFER)
        )
    }

    override fun endTurn() {
        val isGuest = guestNameInteractor.value() == playerName
        val placed = placedInteractor.value()
        val arrived = arrivedInteractor.value()
        if (isGuest) {
            if (!arrived) {
                arrived()
            }
            databaseInteractor.giveTurnToWatchman()
        } else {
            if (!placed) {
                placed()
            }
            databaseInteractor.giveTurnToGuest()
        }
        databaseInteractor.saveMap()
    }

    override fun isGuestData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            guestNameInteractor.get().map {
                val isGuest = it == playerName

                isGuest
            }
                .toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun enableTurnButtonData(): LiveData<Boolean> {
        val observable = Observable.combineLatest(
            playerTurnInteractor.get(),
            guestNameInteractor.get(),
            arrivedInteractor.get(),
            placedInteractor.get(),
            cellsListInteractor.get(),
            Function5 { name: String, guestName: String, arrived: Boolean, placed: Boolean, cells: MutableList<Cell> ->
                //                if (playerName != name) {
//                    return@Function5 false
//                } else {
//                    if (guestName == playerName) {
//                        if (!arrived) {
//                            val filter = cells.filter { it.getType() == Cell.Type.PIER }
//                            val find = filter.find { (it as Pier).persons?.isNotEmpty() ?: false }
//                            find != null
//                        } else {
//                            true
//                        }
//                    } else {
//                        if (!placed) {
//                            cells.filter { it.getType() == Cell.Type.ENGINE }.size == 1 &&
//                                    cells.filter { it.getType() == Cell.Type.BRIDGE }.size == 1 &&
//                                    cells.filter { it.getType() == Cell.Type.REACTOR }.size == 1
//                        } else {
//                            true
//                        }
//                    }
//                }
                true
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

    override fun isArrivedData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            arrivedInteractor.get()
                .toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun placed() {
        databaseInteractor.placed()
    }

    override fun arrived() {
        databaseInteractor.arrived()
    }

    override fun cellsPanelVisibilityData(): LiveData<Boolean> {
        val observable = Observable.combineLatest(
            guestNameInteractor.get(),
            placedInteractor.get(),
            BiFunction { guestName: String, placed: Boolean ->
                guestName != playerName && !placed
            }
        )
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun turnButtonTextData(): LiveData<String> {
        val observable = Observable.combineLatest(
            placedInteractor.get().startWith(false),
            arrivedInteractor.get().startWith(false),
            guestNameInteractor.get(),
            Function3 { placed: Boolean, arrived: Boolean, guestName: String ->
                if (guestName == playerName) {
                    if (!arrived) {
                        "Пристыковаться"
                    } else {
                        "Закончить ход"
                    }
                } else {
                    val watchman = watchmanInteractor.value()
                    watchman.cp = actionPointsInteractor.value().size.toLong()
                    watchmanInteractor.update(watchman)
                    databaseInteractor.saveWatchman()
                    if (!placed) {
                        "Закончить размещение"
                    } else {
                        "Закончить ход"
                    }
                }
            }
        )
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun currentStatusTextData(): LiveData<String> {
        val observable = Observable.combineLatest(
            playerTurnInteractor.get(),
            placedInteractor.get().startWith(false),
            arrivedInteractor.get().startWith(false),
            guestNameInteractor.get(),
            Function4 { name: String, placed: Boolean, arrived: Boolean, guestName: String ->
                if (guestName == playerName) {
                    if (name == playerName) {
                        if (arrived) {
                            "Твой ход"
                        } else {
                            "Нужно выбрать причальный шлюз"
                        }
                    } else {
                        if (!placed) {
                            "Компуктер прячет свои недра..."
                        } else {
                            "ИИ измышляет здодейство..."
                        }
                    }
                } else {
                    if (name == playerName) {
                        if (placed) {
                            "Твой ход"
                        } else {
                            "Нужно расставить реактор, двигатель и мостик"
                        }
                    } else {
                        if (!arrived) {
                            "Пришелец выбирает место для проникновения..."
                        } else {
                            "Пришелец пакостит..."
                        }
                    }
                }
            }
        )
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun increaseMaxActionPoints(points: Int) {
        val value = actionPointsInteractor.value()
        for (i in 1..points) {
            value.add(ActionPoint())
        }
        actionPointsInteractor.update(value)
    }

    @ExperimentalStdlibApi
    override fun decreaseMaxActionPoint(points: Int) {
        val value = actionPointsInteractor.value()
        for (i in 1..points) {
            if (value.isNotEmpty()) {
                value.removeLast()
            }
        }
        actionPointsInteractor.update(value)
    }

    override fun actionPointsData(): LiveData<MutableList<ActionPoint>> {
        return LiveDataReactiveStreams.fromPublisher(
            actionPointsInteractor.get().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun personsData(): LiveData<MutableList<Guest>> {
        return LiveDataReactiveStreams.fromPublisher(
            guestListInteractor.get().toFlowable(BackpressureStrategy.LATEST)
        )
    }
}