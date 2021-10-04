package aldtoll.godswars

import aldtoll.godswars.ext.replaceFragment
import aldtoll.godswars.routing.NowScreen
import aldtoll.godswars.screen.game_screen.guest_screen.GuestsScreen
import aldtoll.godswars.screen.game_screen.watchman_screen.WatchmanScreen
import aldtoll.godswars.screen.map_screen.MapEditorScreen
import aldtoll.godswars.screen.select_player_screen.SelectPlayerScreen
import aldtoll.godswars.screen.start_screen.StartScreen
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val mainViewModel: IMainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.observeData()
        mainViewModel.nowScreen().observe(this, Observer {
            when (it) {
                NowScreen.START_SCREEN -> showStartScreen()
                NowScreen.MAP_SCREEN -> showMapScreen()
                NowScreen.SELECT_PLAYER_SCREEN -> showSelectPlayerScreen()
                NowScreen.GUESTS_SCREEN -> showGuestScreen()
                NowScreen.WATCHMAN_SCREEN -> showWatchmanScreen()
                NowScreen.CLOSE_SCREEN -> closeAll()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mainViewModel.clickAction(item)
        return super.onOptionsItemSelected(item)
    }

    private fun showStartScreen() {
        replaceFragment(StartScreen.newInstance())
    }

    private fun showMapScreen() {
        replaceFragment(MapEditorScreen.newInstance())
    }

    private fun showSelectPlayerScreen() {
        replaceFragment(SelectPlayerScreen.newInstance())
    }

    private fun showGuestScreen() {
        replaceFragment(GuestsScreen.newInstance())
    }

    private fun showWatchmanScreen() {
        replaceFragment(WatchmanScreen.newInstance())
    }
//
//    private fun showQuestScreen() {
//        replaceFragment(QuestScreen.newInstance())
//    }
//
//    private fun showFinishScreen() {
//        replaceFragment(FinishScreen.newInstance())
//    }

    override fun onBackPressed() {
        mainViewModel.onBackPressed()
    }

    private fun closeAll() {
        mainViewModel.onClose()
        finish()
    }
}
