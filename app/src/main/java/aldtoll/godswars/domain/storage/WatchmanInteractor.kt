package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.Watchman
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class WatchmanInteractor : IWatchmanInteractor {

    private val item = BehaviorSubject.create<Watchman>()

    override fun update(watchman: Watchman) {
        item.onNext(watchman)
    }

    override fun get(): Observable<Watchman> = item

    override fun value(): Watchman = item.value ?: Watchman(1)
}