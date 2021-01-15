package aldtoll.godswars.routing

import aldtoll.godswars.NowScreen
import io.reactivex.Observable

class GetNowScreenInteractor(
    private val router: IRouter
) : IGetNowScreenInteractor {

    override fun execute(): Observable<NowScreen> = router.nowScreen()

}