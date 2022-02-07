package aldtoll.godswars.domain.logic

import io.reactivex.Observable

interface ISelectedPersonCardVisibility {

    fun get(): Observable<Boolean>
}