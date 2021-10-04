package aldtoll.godswars

import aldtoll.godswars.routing.NowScreen
import android.view.MenuItem
import androidx.lifecycle.LiveData

interface IMainViewModel {

    fun nowScreen(): LiveData<NowScreen>

    fun onBackPressed()

    fun onClose()

    fun clickAction(menuItem: MenuItem)

    fun observeData()
}