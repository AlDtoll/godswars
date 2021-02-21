package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Guest
import io.reactivex.Observable

interface ISelectedGuestInteractor {

    fun update(guest: Guest)

    fun get(): Observable<Guest>

    fun value(): Guest
}