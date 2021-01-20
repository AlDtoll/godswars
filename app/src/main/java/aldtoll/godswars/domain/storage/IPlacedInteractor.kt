package aldtoll.godswars.domain.storage

import io.reactivex.Observable

interface IPlacedInteractor {

    fun update(placed: Boolean)

    fun get(): Observable<Boolean>

    fun value(): Boolean
}