package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Guest
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SelectedGuestInteractor : ISelectedGuestInteractor {

    private val item = BehaviorSubject.create<Guest>()

    override fun update(guest: Guest) {
        item.onNext(guest)
    }

    override fun get(): Observable<Guest> = item

    override fun value(): Guest = item.value ?: Guest()
}