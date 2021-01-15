package aldtoll.godswars.domain.storage

import io.reactivex.Observable

interface IWatchmanNameInteractor {

    fun update(name: String)

    fun get(): Observable<String>

    fun value(): String

}