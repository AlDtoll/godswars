package aldtoll.godswars

import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.routing.IGetNowScreenInteractor
import aldtoll.godswars.routing.NowScreen
import aldtoll.godswars.routing.OnBackPressedInteractor
import aldtoll.godswars.routing.RouteToStartScreenInteractor
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy

class MainViewModel(
    private val getNowScreenInteractor: IGetNowScreenInteractor,
    private val onBackPressedInteractor: OnBackPressedInteractor,
    private val databaseInteractor: IDatabaseInteractor,
    private val routeToStartScreenInteractor: RouteToStartScreenInteractor
) : IMainViewModel {

    override fun nowScreen(): LiveData<NowScreen> {
        return LiveDataReactiveStreams.fromPublisher(
            getNowScreenInteractor.execute().toFlowable(BackpressureStrategy.LATEST)
        )
    }

    override fun onBackPressed() {
        onBackPressedInteractor.execute()
    }

    override fun observeData() {
        databaseInteractor.observeRealtimeDatabase()
    }

    override fun onClose() {
        routeToStartScreenInteractor.execute()
    }

    override fun clickAction(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.action_init_map -> initMap()
            R.id.action_restart_map -> restartMap()
            R.id.action_restart_player -> restartPlayer()
            R.id.action_turn_to_watchman -> databaseInteractor.giveTurnToWatchman()
            R.id.action_turn_to_guest -> databaseInteractor.giveTurnToGuest()
            R.id.action_unplaced -> databaseInteractor.placed(false)
            R.id.action_unarrived -> databaseInteractor.arrived(false)
            android.R.id.home -> onBackPressed()
        }
    }

    private fun initMap() {
        databaseInteractor.saveMap()
    }

    private fun restartMap() {
        databaseInteractor.clearCells()
    }

    private fun restartPlayer() {
        routeToStartScreenInteractor.execute()
        val pref = App.getPref()
        pref?.run {
            this.edit().putString("playerName", "").apply()
        }
        databaseInteractor.clearPlayerName()

    }
}