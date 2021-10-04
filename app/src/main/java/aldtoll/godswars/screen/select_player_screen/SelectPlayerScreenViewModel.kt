package aldtoll.godswars.screen.select_player_screen

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.storage.*
import aldtoll.godswars.routing.RouteToGuestsScreenInteractor
import aldtoll.godswars.routing.RouteToWatchmanScreenInteractor
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject

class SelectPlayerScreenViewModel(
    private val databaseInteractor: IDatabaseInteractor,
    private val guestNameInteractor: IGuestNameInteractor,
    private val watchmanNameInteractor: IWatchmanNameInteractor,
    private val routeToGuestsScreenInteractor: RouteToGuestsScreenInteractor,
    private val routeToWatchmanScreenInteractor: RouteToWatchmanScreenInteractor,
    private val playerTurnInteractor: IPlayerTurnInteractor,
    private val actionPointsInteractor: IActionPointsInteractor,
    private val watchmanInteractor: IWatchmanInteractor,
    private val guestListInteractor: IGuestListInteractor,
    private val selectedPersonListInteractor: ISelectedPersonListInteractor,
    private val arrivedInteractor: IArrivedInteractor
) : ISelectPlayerScreenViewModel {

    private var guestName = PublishSubject.create<String>()
    private val database = Firebase.database

    override fun selectWatchman(watchmanName: String) {
        val myRef = database.getReference("watchmanName")
        myRef.setValue(watchmanName)
        saveLocalPlayerName(watchmanName)
    }

    override fun selectGuest(guestName: String) {
        val myRef = database.getReference("guestName")
        myRef.setValue(guestName)
        saveLocalPlayerName(guestName)
    }

    private fun saveLocalPlayerName(watchmanName: String) {
        val pref = App.getPref()
        pref?.run {
            this.edit().putString("playerName", watchmanName).apply()
        }
    }

    override fun guestNameChange(name: String) {
        guestName.onNext(name)
    }

    override fun guestNameData(): LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(
            guestNameInteractor.get().toFlowable(
                BackpressureStrategy.LATEST
            )
        )

    override fun watchmanNameData(): LiveData<String> =
        LiveDataReactiveStreams.fromPublisher(
            watchmanNameInteractor.get().toFlowable(
                BackpressureStrategy.LATEST
            )
        )

    override fun startGame() {
        //todo нужно переделать, когда можно будет пропускать экран выбора игрока
        val gameNotStartedYet = playerTurnInteractor.value().isEmpty()
        if (gameNotStartedYet) {
            databaseInteractor.giveTurnToWatchman()
        }
        val playerName = App.getPref()?.getString("playerName", "")
        val isWatchman = playerName != guestNameInteractor.value()
        if (isWatchman) {
            val watchman = watchmanInteractor.value()
            val actionPoints = mutableListOf<ActionPoint>()
            for (i in 1..watchman.maxCp) {
                actionPoints.add(ActionPoint())
            }
            actionPointsInteractor.update(actionPoints)
        } else {
            if (!arrivedInteractor.value()) {
                val guests = guestListInteractor.value()
                selectedPersonListInteractor.update(guests.toMutableList())
            }
        }
        if (isWatchman) {
            routeToWatchmanScreenInteractor.execute()
        } else {
            routeToGuestsScreenInteractor.execute()
        }
    }
}