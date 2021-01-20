package aldtoll.godswars.domain.storage

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class PlacedInteractor : IPlacedInteractor {

    private val item = BehaviorSubject.create<Boolean>()

    override fun update(placed: Boolean) {
        item.onNext(placed)
    }

    override fun get(): Observable<Boolean> = item

    override fun value(): Boolean = item.value ?: false
}