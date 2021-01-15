package aldtoll.godswars.routing

class RouteToSelectPlayerScreenInteractor(
    private val router: IRouter
) {

    fun execute() {
        router.routeToSelectPlayerScreen()
    }
}