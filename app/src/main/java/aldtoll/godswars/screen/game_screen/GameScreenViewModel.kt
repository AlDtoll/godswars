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
import java.util.*

class GameScreenViewModel(
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor,
    private val guestNameInteractor: IGuestNameInteractor,
    private val playerTurnInteractor: IPlayerTurnInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor
) : IGameScreenViewModel {

    override fun saveCells(cells: ArrayList<Cell>) {
        cellsListInteractor.update(cells)
    }

    override fun cellsData(): LiveData<MutableList<Cell>> {
        return LiveDataReactiveStreams.fromPublisher(
            cellsListInteractor.get().toFlowable(BackpressureStrategy.BUFFER)
        )
    }

    override fun endTurn(isGuest: Boolean) {
        if (isGuest) {
            databaseInteractor.giveTurnToWatchman()
        } else {
            databaseInteractor.giveTurnToGuest()
        }
        databaseInteractor.saveMap()
    }

    override fun isGuestData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            guestNameInteractor.get().map { it == App.getPref()?.getString("playerName", "") }
                .toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun isYourTurnData(): LiveData<Boolean> {
        return LiveDataReactiveStreams.fromPublisher(
            playerTurnInteractor.get().map { it == App.getPref()?.getString("playerName", "") }
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
                guestName != App.getPref()?.getString("playerName", "") && !placed
            }
        )
        return LiveDataReactiveStreams.fromPublisher(
            observable.toFlowable(BackpressureStrategy.LATEST)
        )
    }
}