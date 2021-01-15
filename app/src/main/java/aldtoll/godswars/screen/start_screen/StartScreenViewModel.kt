package aldtoll.godswars.screen.start_screen

import aldtoll.godswars.routing.RouteToMapScreenIntreactor
import aldtoll.godswars.routing.RouteToSelectPlayerScreenInteractor

class StartScreenViewModel(
    private val routeToMapScreenIntreactor: RouteToMapScreenIntreactor,
    private val routeToSelectPlayerScreenInteractor: RouteToSelectPlayerScreenInteractor
) : IStartScreenViewModel {

    override fun startGameClicked() {
        routeToSelectPlayerScreenInteractor.execute()
    }

    override fun createMapClicked() {
        routeToMapScreenIntreactor.execute()
    }
}