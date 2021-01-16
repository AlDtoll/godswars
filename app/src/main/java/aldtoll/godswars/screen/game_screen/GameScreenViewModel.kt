package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.domain.model.Cell
import aldtoll.godswars.domain.storage.ICellsListInteractor
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import java.util.*

class GameScreenViewModel(
    private val cellsListInteractor: ICellsListInteractor
) : IGameScreenViewModel {

    override fun saveCells(cells: ArrayList<Cell>) {
        cellsListInteractor.update(cells)
    }

    override fun cellsData(): LiveData<MutableList<Cell>> {
        return LiveDataReactiveStreams.fromPublisher(
            cellsListInteractor.get().toFlowable(BackpressureStrategy.BUFFER)
        )
    }
}