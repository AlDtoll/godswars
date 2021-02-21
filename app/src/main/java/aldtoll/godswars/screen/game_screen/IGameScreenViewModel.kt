package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.Cell
import androidx.lifecycle.LiveData
import java.util.*

interface IGameScreenViewModel {

    fun saveCellsLocal(cells: ArrayList<Cell>)

    fun cellsData(): LiveData<MutableList<Cell>>

    fun endTurn()

    fun isGuestData(): LiveData<Boolean>

    fun enableTurnButtonData(): LiveData<Boolean>

    fun isPlacedData(): LiveData<Boolean>

    fun isArrivedData(): LiveData<Boolean>

    fun placed()

    fun arrived()

    fun cellsPanelVisibilityData(): LiveData<Boolean>

    fun turnButtonTextData(): LiveData<String>

    fun currentStatusTextData(): LiveData<String>

    fun actionPointsData(): LiveData<MutableList<ActionPoint>>

    fun increaseMaxActionPoints(points: Int)

    fun decreaseMaxActionPoint(points: Int)

}