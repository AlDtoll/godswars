package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.unit.Guest
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class GuestListInteractor : IGuestListInteractor {

    private val list = BehaviorSubject.create<MutableList<Guest>>()

    override fun update(guests: MutableList<Guest>) {
        list.onNext(guests)
    }

    override fun get(): Observable<MutableList<Guest>> = list

    override fun value(): MutableList<Guest> = list.value ?: emptyList<Guest>().toMutableList()
}