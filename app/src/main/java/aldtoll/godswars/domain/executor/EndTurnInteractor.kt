package aldtoll.godswars.domain.executor

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.storage.IArrivedInteractor
import aldtoll.godswars.domain.storage.IGuestNameInteractor
import aldtoll.godswars.domain.storage.IPlacedInteractor
import aldtoll.godswars.domain.storage.ISelectedPersonListInteractor

class EndTurnInteractor(
    private val guestNameInteractor: IGuestNameInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val databaseInteractor: IDatabaseInteractor
) : IEndTurnInteractor {

    private val playerName = App.getPref()?.getString("playerName", "")

    override fun execute() {
        val isGuest = guestNameInteractor.value() == playerName
        val placed = placedInteractor.value()
        val arrived = arrivedInteractor.value()
        if (isGuest) {
            if (!arrived) {
                databaseInteractor.arrived(true)
                selectedPersonListInteractor.update(mutableListOf())
            }
            databaseInteractor.giveTurnToWatchman()
        } else {
            if (!placed) {
                databaseInteractor.placed(true)
            }
            databaseInteractor.giveTurnToGuest()
        }
        databaseInteractor.saveMap()
    }
}