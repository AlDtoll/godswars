package aldtoll.godswars.routing

class OnBackPressedInteractor(
    private val router: IRouter
) {

    fun execute() {
        router.onBackPressed()
    }
}