package aldtoll.godswars.domain.logic

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.domain.storage.*
import io.reactivex.Observable

class SelectedPersonListInteractor(
    private val guestNameInteractor: IGuestNameInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val selectedPersonInteractor: ISelectedPersonInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    private val cellsListInteractor: ICellsListInteractor,
    private val remoteGuestListInteractor: IRemoteGuestListInteractor,
    private val selectedCellInteractor: ISelectedCellInteractor
) : ISelectedPersonListInteractor {

    private val localPlayerName = App.getPref()?.getString("playerName", "")

    override fun get(): Observable<List<Person>> {
        val observable = Observable.combineLatest(
            arrivedInteractor.get(),
            guestNameInteractor.get(),
            remoteGuestListInteractor.get(),
            selectedCellInteractor.get()
        ) { arrived: Boolean, guestName: String, remoteGuestsList, selectedCell: Cell ->
            val isGuest = guestName == localPlayerName
            if (isGuest) {
                if (arrived) {
                    if (selectedCell != Cell.nothing()) {
                        selectedCell.persons ?: mutableListOf()
                    } else {
                        mutableListOf<Person>()
                    }
                } else {
                    remoteGuestsList.map {
                        it
                    }
                }
            } else {
                mutableListOf()
            }
        }
        return observable
    }
}