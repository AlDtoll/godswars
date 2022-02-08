package aldtoll.godswars.domain.logic

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.domain.storage.IArrivedInteractor
import aldtoll.godswars.domain.storage.ICellsListInteractor
import aldtoll.godswars.domain.storage.IGuestNameInteractor
import aldtoll.godswars.domain.storage.IPlacedInteractor
import io.reactivex.Observable

class ActionPointsInteractor(
    private val guestNameInteractor: IGuestNameInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val selectedPersonInteractor: ISelectedPersonInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor
) : IActionPointsInteractor {

    private val localPlayerName = App.getPref()?.getString("playerName", "")

    override fun get(): Observable<MutableList<ActionPoint>> {
        val observable = Observable.combineLatest(
            selectedPersonInteractor.get(),
            arrivedInteractor.get(),
            guestNameInteractor.get(),
        ) { person: Person, arrived: Boolean, guestName: String ->
            val isGuest = guestName == localPlayerName
            if (isGuest) {
                val actionPoints = mutableListOf<ActionPoint>()
                if (person != Person.nobody()) {
                    for (i in 1..person.maxAp) {
                        actionPoints.add(ActionPoint())
                    }
                    for (i in 0 until person.ap) {
                        actionPoints[i.toInt()].active = true
                    }
                }
                actionPoints
            } else {
                mutableListOf()
            }
        }
        return observable
    }
}