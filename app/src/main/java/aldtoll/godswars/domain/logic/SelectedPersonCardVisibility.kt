package aldtoll.godswars.domain.logic

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.domain.storage.*
import io.reactivex.Observable

class SelectedPersonCardVisibility(
    private val guestNameInteractor: IGuestNameInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val selectedPersonInteractor: ISelectedPersonInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor
) : ISelectedPersonCardVisibility {

    private val localPlayerName = App.getPref()?.getString("playerName", "")

    override fun get(): Observable<Boolean> {
        val observable = Observable.combineLatest(
            selectedPersonInteractor.get(),
            arrivedInteractor.get(),
            guestNameInteractor.get(),
        ) { person: Person, arrived: Boolean, guestName: String ->
            val isGuest = guestName == localPlayerName
            if (isGuest) {
                if (arrived) {
                    person != Person.nobody()
                } else {
                    false
                }
            } else {
                //todo
                false
            }
        }
        return observable
    }
}