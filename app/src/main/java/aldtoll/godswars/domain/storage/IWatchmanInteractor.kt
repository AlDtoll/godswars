package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.Watchman
import io.reactivex.Observable

interface IWatchmanInteractor {

    fun update(watchman: Watchman)

    fun get(): Observable<Watchman>

    fun value(): Watchman
}