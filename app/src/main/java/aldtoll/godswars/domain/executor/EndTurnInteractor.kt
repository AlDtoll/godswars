package aldtoll.godswars.domain.executor

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.logic.ISelectedPersonListInteractor
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.domain.storage.IArrivedInteractor
import aldtoll.godswars.domain.storage.ICellsListInteractor
import aldtoll.godswars.domain.storage.IGuestNameInteractor
import aldtoll.godswars.domain.storage.IPlacedInteractor

class EndTurnInteractor(
    private val guestNameInteractor: IGuestNameInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor,
) : IEndTurnInteractor {

    private val localPlayerName = App.getPref()?.getString("playerName", "")

    override fun execute() {
        val isGuest = guestNameInteractor.value() == localPlayerName
        val placed = placedInteractor.value()
        val arrived = arrivedInteractor.value()
        if (isGuest) {
            if (!arrived) {
                databaseInteractor.arrived(true)
            }
            updateActionPoints()
            databaseInteractor.giveTurnToWatchman()
        } else {
            if (!placed) {
                databaseInteractor.placed(true)
            }
            updateActionPoints()
            databaseInteractor.giveTurnToGuest()
        }
        databaseInteractor.saveMap()
    }

    private fun updateActionPoints() {
        val filter = cellsListInteractor.value().filter { it.persons != null }
        filter.forEach { it.persons?.forEach { person: Person -> person.ap = person.maxAp } }
    }
}