package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.domain.model.Cell
import androidx.lifecycle.LiveData
import java.util.*

interface IGameScreenViewModel {

    fun saveCells(cells: ArrayList<Cell>)

    fun cellsData(): LiveData<MutableList<Cell>>
}