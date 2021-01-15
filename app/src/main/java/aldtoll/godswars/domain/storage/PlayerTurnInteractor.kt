package aldtoll.godswars.domain.storage

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class PlayerTurnInteractor : IPlayerTurnInteractor {

    private val item = BehaviorSubject.create<String>()

    override fun update(name: String) {
        item.onNext(name)
    }

    override fun get(): Observable<String> = item

    override fun value(): String = item.value ?: ""
}