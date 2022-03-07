package aldtoll.godswars.domain.logic

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.domain.storage.*
import io.reactivex.Observable

class SelectedPersonInteractor(
    private val guestNameInteractor: IGuestNameInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val personInteractor: IPersonInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor
) : ISelectedPersonInteractor {

    private val localPlayerName = App.getPref()?.getString("playerName", "")

    override fun data(): Observable<Person> {
        val observable = Observable.combineLatest(
            personInteractor.get(),
            arrivedInteractor.get(),
            guestNameInteractor.get(),
        ) { person: Person, arrived: Boolean, guestName: String ->
            val isGuest = guestName == localPlayerName
            if (isGuest) {
                if (arrived) {
                    person
                } else {
                    Person.nobody()
                }
            } else {
                Person.nobody()
            }
        }
        return observable
    }

}