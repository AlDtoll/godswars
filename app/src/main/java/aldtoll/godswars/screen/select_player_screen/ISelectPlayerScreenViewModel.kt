package aldtoll.godswars.screen.select_player_screen

import androidx.lifecycle.LiveData

interface ISelectPlayerScreenViewModel {

    fun selectGuest()

    fun selectWatchman()

    fun guestNameChange(name: String)

    fun guestNameData(): LiveData<String>

    fun watchmanNameData(): LiveData<String>

    fun startGame()
}