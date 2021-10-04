package aldtoll.godswars.routing

class RouteToWatchmanScreenInteractor(
    private val router: IRouter
) {

    fun execute() {
        router.routeToWatchmanScreen()
    }
}