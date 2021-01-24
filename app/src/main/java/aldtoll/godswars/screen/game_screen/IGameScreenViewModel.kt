package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.domain.model.Cell
import androidx.lifecycle.LiveData
import java.util.*

interface IGameScreenViewModel {

    fun saveCells(cells: ArrayList<Cell>)

    fun cellsData(): LiveData<MutableList<Cell>>

    fun endTurn(isGuest: Boolean)

    fun isGuestData(): LiveData<Boolean>

    fun isYourTurnData(): LiveData<Boolean>

    fun isPlacedData(): LiveData<Boolean>

    fun isArrivedData(): LiveData<Boolean>

    fun placed()

    fun arrived()

    fun cellsPanelVisibilityData(): LiveData<Boolean>

}