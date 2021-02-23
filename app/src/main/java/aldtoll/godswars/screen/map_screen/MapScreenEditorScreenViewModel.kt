package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.storage.ICellsListInteractor
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import java.util.*

class MapScreenEditorScreenViewModel(
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor
) : IMapEditorScreenViewModel {

    override fun initEmptyCells() {
        databaseInteractor.clearCells()
    }

    override fun saveCells(cells: ArrayList<Cell>) {
        cellsListInteractor.update(cells)
    }

    override fun cellsData(): LiveData<MutableList<Cell>> {
        return LiveDataReactiveStreams.fromPublisher(
            cellsListInteractor.get().toFlowable(BackpressureStrategy.BUFFER)
        )
    }

}