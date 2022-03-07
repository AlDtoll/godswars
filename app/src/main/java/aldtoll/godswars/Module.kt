package aldtoll.godswars

import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.executor.EndTurnInteractor
import aldtoll.godswars.domain.executor.IEndTurnInteractor
import aldtoll.godswars.domain.logic.*
import aldtoll.godswars.domain.storage.*
import aldtoll.godswars.routing.*
import aldtoll.godswars.screen.game_screen.guest_screen.GuestsScreenViewModel
import aldtoll.godswars.screen.game_screen.guest_screen.IGuestsScreenViewModel
import aldtoll.godswars.screen.game_screen.watchman_screen.IWatchmanScreenViewModel
import aldtoll.godswars.screen.game_screen.watchman_screen.WatchmanScreenViewModel
import aldtoll.godswars.screen.map_screen.IMapEditorScreenViewModel
import aldtoll.godswars.screen.map_screen.MapScreenEditorScreenViewModel
import aldtoll.godswars.screen.select_player_screen.ISelectPlayerScreenViewModel
import aldtoll.godswars.screen.select_player_screen.SelectPlayerScreenViewModel
import aldtoll.godswars.screen.start_screen.IStartScreenViewModel
import aldtoll.godswars.screen.start_screen.StartScreenViewModel
import org.koin.dsl.module.module

val appModule = module {
    single { this }
    single {
        MainViewModel(
            get(),
            get(),
            get(),
            get()
        ) as IMainViewModel
    }

    single { StartScreenViewModel(get(), get()) as IStartScreenViewModel }
    single { MapScreenEditorScreenViewModel(get(), get()) as IMapEditorScreenViewModel }
    single {
        GuestsScreenViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) as IGuestsScreenViewModel
    }
    single {
        WatchmanScreenViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) as IWatchmanScreenViewModel
    }
    single {
        SelectPlayerScreenViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) as ISelectPlayerScreenViewModel
    }

    single {
        DatabaseInteractor(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) as IDatabaseInteractor
    }

    single { CellsListInteractor() as ICellsListInteractor }
    single { GuestNameInteractor() as IGuestNameInteractor }
    single { WatchmanNameInteractor() as IWatchmanNameInteractor }
    single { PlayerTurnInteractor() as IPlayerTurnInteractor }
    single { PlacedInteractor() as IPlacedInteractor }
    single { ArrivedInteractor() as IArrivedInteractor }
    single {
        ActionPointsInteractor(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) as IActionPointsInteractor
    }
    single { RemoteGuestListInteractor() as IRemoteGuestListInteractor }
    single { PersonInteractor() as IPersonInteractor }
    single { WatchmanInteractor() as IWatchmanInteractor }
    single { CellInteractor() as ICellInteractor }

    single { EndTurnInteractor(get(), get(), get(), get(), get(), get()) as IEndTurnInteractor }

    single {
        SelectedPersonInteractor(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) as ISelectedPersonInteractor
    }

    single {
        SelectedPersonListInteractor(
            get(),
            get(),
            get(),
            get(),
            get(), get(), get(), get(), get()
        ) as ISelectedPersonListInteractor
    }


    single { Router() as IRouter }
    single { GetNowScreenInteractor(get()) as IGetNowScreenInteractor }
    single { RouteToStartScreenInteractor(get()) }
    single { RouteToMapScreenIntreactor(get()) }
    single { RouteToSelectPlayerScreenInteractor(get()) }
    single { RouteToGuestsScreenInteractor(get()) }
    single { RouteToWatchmanScreenInteractor(get()) }
    single { OnBackPressedInteractor(get()) }
}