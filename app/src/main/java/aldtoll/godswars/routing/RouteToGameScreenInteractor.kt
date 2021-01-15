package aldtoll.godswars.routing

class RouteToGameScreenInteractor(
    private val router: IRouter
) {

    fun execute() {
        router.routeToGameScreen()
    }
}