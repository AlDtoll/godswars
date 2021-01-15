package aldtoll.godswars.routing

import aldtoll.godswars.NowScreen
import io.reactivex.Observable

interface IRouter {

    fun nowScreen(): Observable<NowScreen>

    fun routeToStartScreen()

    fun routeToMapScreen()

    fun routeToSelectPlayerScreen()

    fun routeToGameScreen()

    fun onBackPressed()
}