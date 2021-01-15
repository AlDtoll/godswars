package aldtoll.godswars

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
        const val PLAYER_INFORMATION = "player_information"

        fun getPref(): SharedPreferences? {
            return instance.getSharedPreferences(
                PLAYER_INFORMATION,
                AppCompatActivity.MODE_PRIVATE
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
        instance = this
    }


}