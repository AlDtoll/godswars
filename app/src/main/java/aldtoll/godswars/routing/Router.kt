package aldtoll.godswars.routing

import aldtoll.godswars.NowScreen
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class Router : IRouter {

    private val nowScreen = BehaviorSubject.create<NowScreen>()
    private val pressBackButtonEvents = PublishSubject.create<Unit>()

    override fun nowScreen(): Observable<NowScreen> = nowScreen.startWith(NowScreen.START_SCREEN)

    override fun routeToStartScreen() {
        nowScreen.onNext(NowScreen.START_SCREEN)
    }

    override fun routeToMapScreen() {
        nowScreen.onNext(NowScreen.MAP_SCREEN)
    }

    override fun routeToSelectPlayerScreen() {
        nowScreen.onNext(NowScreen.SELECT_PLAYER_SCREEN)
    }

    override fun routeToGameScreen() {
        nowScreen.onNext(NowScreen.GAME_SCREEN)
    }

    override fun onBackPressed() {
        pressBackButtonEvents.onNext(Unit)
        val previousScreen =
            getPreviousScreen(nowScreen.value ?: NowScreen.START_SCREEN)
        nowScreen.onNext(previousScreen)
    }

    private fun getPreviousScreen(nowScreen: NowScreen): NowScreen {
        return when (nowScreen) {
            NowScreen.MAP_SCREEN -> NowScreen.START_SCREEN
            NowScreen.SELECT_PLAYER_SCREEN -> NowScreen.START_SCREEN
            NowScreen.GAME_SCREEN -> NowScreen.START_SCREEN
            else -> NowScreen.CLOSE_SCREEN
        }
    }
}