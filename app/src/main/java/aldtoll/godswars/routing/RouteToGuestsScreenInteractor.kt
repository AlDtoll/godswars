package aldtoll.godswars.routing

class RouteToGuestsScreenInteractor(
    private val router: IRouter
) {

    fun execute() {
        router.routeToGuestsScreen()
    }
}