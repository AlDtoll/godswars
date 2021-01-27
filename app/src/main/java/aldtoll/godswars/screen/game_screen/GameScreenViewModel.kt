package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.Cell
import aldtoll.godswars.domain.storage.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import java.util.*

class GameScreenViewModel(
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor,
    private val guestNameInteractor: IGuestNameInteractor,
    private val playerTurnInteractor: IPlayerTurnInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor
) : IGameScreenViewModel {

    private val playerName = App.getPref()?.getString("playerName", "")

    override fun saveCells(cells: ArrayList<Cell>) {
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
            guestNameInteractor.get().map { it == playerName }
                .toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun isYourTurnData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            playerTurnInteractor.get().map { it == playerName }
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
}