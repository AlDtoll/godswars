package aldtoll.godswars.screen.game_screen.watchman_screen

import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.unit.Person
import androidx.lifecycle.LiveData
import java.util.*

interface IWatchmanScreenViewModel {

    fun saveCellsLocal(cells: ArrayList<Cell>)

    fun cellsData(): LiveData<MutableList<Cell>>

    fun endTurn()

    fun enableTurnButtonData(): LiveData<Boolean>

    fun isPlacedData(): LiveData<Boolean>

    fun cellsPanelVisibilityData(): LiveData<Boolean>

    fun turnButtonTextData(): LiveData<String>

    fun currentStatusTextData(): LiveData<String>

    fun actionPointsData(): LiveData<MutableList<ActionPoint>>

    fun increaseMaxActionPoints(points: Int)

    fun decreaseMaxActionPoint(points: Int)

    fun personsData(): LiveData<MutableList<Person>>

    fun selectPerson(person: Person)

    fun selectedPersonData(): LiveData<Person>

    fun clickCell(item: Cell)
}