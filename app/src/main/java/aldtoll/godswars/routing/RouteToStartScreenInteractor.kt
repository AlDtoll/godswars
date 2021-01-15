package aldtoll.godswars.routing

class RouteToStartScreenInteractor(
    private val router: IRouter
) {

    fun execute() {
        router.routeToStartScreen()
    }
}