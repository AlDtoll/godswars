package aldtoll.godswars.routing

import aldtoll.godswars.NowScreen
import io.reactivex.Observable

interface IGetNowScreenInteractor {

    fun execute(): Observable<NowScreen>
}