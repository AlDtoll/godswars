package aldtoll.godswars.routing

import io.reactivex.Observable

interface IRouter {

    fun nowScreen(): Observable<NowScreen>

    fun routeToStartScreen()

    fun routeToMapScreen()

    fun routeToSelectPlayerScreen()

    fun routeToGuestsScreen()

    fun routeToWatchmanScreen()

    fun onBackPressed()
}