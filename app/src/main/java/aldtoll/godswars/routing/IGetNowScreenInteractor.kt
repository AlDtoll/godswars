package aldtoll.godswars.routing

import io.reactivex.Observable

interface IGetNowScreenInteractor {

    fun execute(): Observable<NowScreen>
}