package aldtoll.godswars.screen.game_screen.guest_screen

import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.unit.Person
import androidx.lifecycle.LiveData

interface IGuestsScreenViewModel {

    fun saveCellsLocal(cells: ArrayList<Cell>)

    fun cellsData(): LiveData<MutableList<Cell>>

    fun endTurn()

    fun enableTurnButtonData(): LiveData<Boolean>

    fun isArrivedData(): LiveData<Boolean>

    fun turnButtonTextData(): LiveData<String>

    fun currentStatusTextData(): LiveData<String>

    fun actionPointsData(): LiveData<MutableList<ActionPoint>>

    fun selectedPersonsData(): LiveData<List<Person>>

    fun clickPerson(person: Person)

    fun selectedPersonData(): LiveData<Person>

    fun clickCell(item: Cell)
}