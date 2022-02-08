package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Guest
import io.reactivex.Observable

interface IRemoteGuestListInteractor {

    fun update(guests: MutableList<Guest>)

    fun get(): Observable<MutableList<Guest>>

    fun value(): MutableList<Guest>
}