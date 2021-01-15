package aldtoll.godswars.screen.select_player_screen

import aldtoll.godswars.App
import aldtoll.godswars.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_select_player.*
import org.koin.android.ext.android.inject

class SelectPlayerScreen : Fragment() {

    companion object {
        fun newInstance(): SelectPlayerScreen =
            SelectPlayerScreen()
    }

    private val selectPlayerScreenViewModel: ISelectPlayerScreenViewModel by inject()
    private var remoteGuestName = ""
    private var remoteWatchmanName = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val pref = App.getPref()
        val database = Firebase.database

        selectGuestButton.setOnClickListener {

            val myRef = database.getReference("guestName")
            val name = guestName.text.toString()
            myRef.setValue(name)

            pref?.run {
                this.edit().putString("guestName", name).apply()
            }
        }

        selectWatchmanButton.setOnClickListener {
            val myRef = database.getReference("watchmanName")
            val name = watchmanName.text.toString()
            myRef.setValue(name)

            pref?.run {
                this.edit().putString("watchmanName", name).apply()
            }
        }

        startGameButton.setOnClickListener {
            selectPlayerScreenViewModel.startGame()
        }

        selectPlayerScreenViewModel.guestNameData().observe(viewLifecycleOwner, Observer {
            it?.run {
                remoteGuestName = it
                defineElementStatus()
            }
        })

        selectPlayerScreenViewModel.watchmanNameData().observe(viewLifecycleOwner, Observer {
            it?.run {
                remoteWatchmanName = it
                defineElementStatus()
            }
        })
    }

    private fun defineElementStatus() {
        val pref = App.getPref()
        val storedGuestName = pref?.getString("guestName", "") ?: ""
        val storedWatchmanName = pref?.getString("watchmanName", "") ?: ""
        if (remoteGuestName.isNotEmpty()) {
            selectGuestButton.isEnabled = false
            guestName.setText(remoteGuestName)
            guestName.isEnabled = false
            if (storedGuestName.isNotEmpty()) {
                if (storedGuestName == remoteGuestName) {
                    guestDescription.text = "вы уже гость"
                    watchmanName.isEnabled = false
                    selectWatchmanButton.isEnabled = false
                } else {
                    guestDescription.text = "место гостя уже занял $remoteGuestName"
                }
            }
        } else {
            if (storedWatchmanName.isEmpty() || remoteWatchmanName.isEmpty() || storedWatchmanName != remoteWatchmanName) {
                selectGuestButton.isEnabled = true
                guestName.setText("")
                guestName.isEnabled = true
                guestDescription.text = ""
            }
        }

        if (remoteWatchmanName.isNotEmpty()) {
            selectWatchmanButton.isEnabled = false
            watchmanName.setText(remoteWatchmanName)
            watchmanName.isEnabled = false
            if (storedWatchmanName.isNotEmpty()) {
                if (storedWatchmanName == remoteWatchmanName) {
                    watchmanDescription.text = "вы уже ИИ"
                    watchmanName.isEnabled = false
                    selectGuestButton.isEnabled = false
                } else {
                    watchmanDescription.text = "место ИИ уже занял $remoteWatchmanName"
                }
            }
        } else {
            if (storedGuestName.isEmpty() || remoteGuestName.isEmpty() || storedGuestName != remoteGuestName) {
                selectWatchmanButton.isEnabled = true
                watchmanName.setText("")
                watchmanName.isEnabled = true
                watchmanDescription.text = ""
            }
        }

        if (remoteWatchmanName.isEmpty() && remoteGuestName.isEmpty()) {
            currentStatusGame.text = "здесь пока еще никого нет "
            startGameButton.isEnabled = false
        }
        if (remoteWatchmanName.isEmpty() && remoteGuestName.isNotEmpty()) {
            currentStatusGame.text = "гость $remoteGuestName ожидает ИИ "
            startGameButton.isEnabled = false
        }
        if (remoteWatchmanName.isNotEmpty() && remoteGuestName.isEmpty()) {
            currentStatusGame.text = "ИИ $remoteWatchmanName ожидает гостя "
            startGameButton.isEnabled = false
        }
        if (remoteWatchmanName.isNotEmpty() && remoteGuestName.isNotEmpty()) {
            currentStatusGame.text = "все на месте, можно начинать"
            startGameButton.isEnabled = true
        }
    }
}