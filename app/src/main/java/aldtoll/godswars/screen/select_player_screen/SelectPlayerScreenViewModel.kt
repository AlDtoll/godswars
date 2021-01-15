package aldtoll.godswars.screen.select_player_screen

import aldtoll.godswars.App
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.storage.IGuestNameInteractor
import aldtoll.godswars.domain.storage.IWatchmanNameInteractor
import aldtoll.godswars.routing.RouteToGameScreenInteractor
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject

class SelectPlayerScreenViewModel(
    private val databaseInteractor: IDatabaseInteractor,
    private val guestNameInteractor: IGuestNameInteractor,
    private val watchmanNameInteractor: IWatchmanNameInteractor,
    private val routeToGameScreenInteractor: RouteToGameScreenInteractor
) : ISelectPlayerScreenViewModel {

    private var guestName = PublishSubject.create<String>()

    override fun selectWatchman() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selectGuest() {
        val pref = App.getPref()
        pref?.run {
            //            this.edit().putString("Name", guestName).apply()
        }
        databaseInteractor.saveGuestName()
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
//        databaseInteractor.savePlayerTurn()
        routeToGameScreenInteractor.execute()
    }
}