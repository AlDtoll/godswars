package aldtoll.godswars.screen.select_player_screen

import aldtoll.godswars.App
import aldtoll.godswars.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_select_player.*
import org.koin.android.ext.android.inject

class SelectPlayerScreen : Fragment() {

    companion object {
        fun newInstance(): SelectPlayerScreen =
            SelectPlayerScreen()
    }

    private val selectPlayerScreenViewModel: ISelectPlayerScreenViewModel by inject()
    private var remoteGuestName = ""
    private var remoteWatchmanName = ""

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

        selectGuestButton.setOnClickListener {
            selectPlayerScreenViewModel.selectGuest(guestName.text.toString())
        }

        selectWatchmanButton.setOnClickListener {
            selectPlayerScreenViewModel.selectWatchman(watchmanName.text.toString())
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
        val storedName = pref?.getString("playerName", "") ?: ""
        guestName.setText(remoteGuestName)
        watchmanName.setText(remoteWatchmanName)
        if (remoteGuestName.isNotEmpty()) {
            selectGuestButton.isEnabled = false
            guestName.isEnabled = false
        } else {
            selectGuestButton.isEnabled = true
            guestName.isEnabled = true
        }
        if (remoteWatchmanName.isNotEmpty()) {
            selectWatchmanButton.isEnabled = false
            watchmanName.isEnabled = false
        } else {
            selectWatchmanButton.isEnabled = true
            watchmanName.isEnabled = true
        }

        if (storedName.isNotEmpty()) {
            when (storedName) {
                remoteGuestName -> {
                    guestDescription.text = "???? ?????? ??????????"
                    selectWatchmanButton.isEnabled = false
                    watchmanName.isEnabled = false
                }
                remoteWatchmanName -> {
                    watchmanDescription.text = "???? ?????? ????"
                    selectGuestButton.isEnabled = false
                    guestName.isEnabled = false
                }
                else -> {
                    currentStatusGame.text =
                        "???????? ?????? $storedName ???? ?????????????????????? ???? ??????????, ???? ????. ???????????????? ??????-???? ?????????????? ????????, ???????????????????? ???????????????? ?????????? ?????????????? ????????????????"
                    startGameButton.isEnabled = false
                    guestDescription.text = ""
                    watchmanDescription.text = ""
                }
            }
        } else {
            if (remoteGuestName.isNotEmpty()) {
                guestDescription.text = "?????????? ?????????? ?????? ?????????? $remoteGuestName"
            } else {
                guestDescription.text = ""
            }
            if (remoteWatchmanName.isNotEmpty()) {
                watchmanDescription.text = "?????????? ???? ?????? ?????????? $remoteWatchmanName"
            } else {
                watchmanDescription.text = ""
            }
        }

        if (remoteWatchmanName.isEmpty() && remoteGuestName.isEmpty()) {
            currentStatusGame.text = "?????????? ???????? ?????? ???????????? ?????? "
            startGameButton.isEnabled = false
        }
        if (remoteWatchmanName.isEmpty() && remoteGuestName.isNotEmpty()) {
            currentStatusGame.text = "?????????? $remoteGuestName ?????????????? ???? "
            startGameButton.isEnabled = false
        }
        if (remoteWatchmanName.isNotEmpty() && remoteGuestName.isEmpty()) {
            currentStatusGame.text = "???? $remoteWatchmanName ?????????????? ?????????? "
            startGameButton.isEnabled = false
        }
        if (remoteWatchmanName.isNotEmpty() && remoteGuestName.isNotEmpty()) {
            currentStatusGame.text = "?????? ???? ??????????, ?????????? ????????????????"
            startGameButton.isEnabled = true
        }
    }
}