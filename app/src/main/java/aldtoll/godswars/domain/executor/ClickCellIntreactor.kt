package aldtoll.godswars.domain.executor

import aldtoll.godswars.App
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.storage.IArrivedInteractor
import aldtoll.godswars.domain.storage.IGuestNameInteractor
import aldtoll.godswars.domain.storage.ISelectedPersonListInteractor

class ClickCellIntreactor(
    private val guestNameInteractor: IGuestNameInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor
) : IClickCellIntreactor {

    private val playerName = App.getPref()?.getString("playerName", "")

    override fun execute(cell: Cell) {
//        if (guestNameInteractor.value() == playerName) {
//            if (!arrivedInteractor.value()) {
//                if (cell.room.type == Room.Type.PIER) {
//                    val selectedGuests =  selectedPersonListInteractor.value()
//                    if (cell.room.persons == selectedGuests) {
//                        cell.room.persons = arrayListOf()
//                        starShip.hide(cell.position.toInt())
//                    } else {
//                        val piers =
//                            cellsListInteractor.value().filter { it.room.type == Room.Type.PIER }
//                        piers.forEach { pier ->
//                            pier.room.persons = arrayListOf()
//                            pier.hide()
//                        }
//                        cell.room.persons = selectedGuests
//                        starShip.show(cell.position.toInt())
//                    }
//                }
//            } else {
//                starShip.selectCell(cell.position.toInt())
//                selectPersons(cell.room.persons!!)
//            }
//        }
//        cellsListInteractor.update(starShip.cells)
    }
}