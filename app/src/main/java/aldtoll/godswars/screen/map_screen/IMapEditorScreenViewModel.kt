package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.domain.model.Cell
import androidx.lifecycle.LiveData
import java.util.*

interface IMapEditorScreenViewModel {

    fun initEmptyCells()

    fun saveCells(cells: ArrayList<Cell>)

    fun cellsData(): LiveData<MutableList<Cell>>
}