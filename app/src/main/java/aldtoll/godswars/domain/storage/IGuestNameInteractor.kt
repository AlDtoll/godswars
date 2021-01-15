package aldtoll.godswars.domain.storage

import io.reactivex.Observable

interface IGuestNameInteractor {

    fun update(name: String)

    fun get(): Observable<String>

    fun value(): String

}