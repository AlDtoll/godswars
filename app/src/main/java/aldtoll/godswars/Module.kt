package aldtoll.godswars

import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.IDatabaseInteractor
import aldtoll.godswars.domain.storage.*
import aldtoll.godswars.routing.*
import aldtoll.godswars.screen.game_screen.GameScreenViewModel
import aldtoll.godswars.screen.game_screen.IGameScreenViewModel
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
    single { GameScreenViewModel(get(), get(), get(), get(), get(), get()) as IGameScreenViewModel }
    single {
        SelectPlayerScreenViewModel(
            get(),
            get(),
            get(),
            get()
        ) as ISelectPlayerScreenViewModel
    }

    single { DatabaseInteractor(get(), get(), get(), get(), get(), get()) as IDatabaseInteractor }

    single { CellsListInteractor() as ICellsListInteractor }
    single { GuestNameInteractor() as IGuestNameInteractor }
    single { WatchmanNameInteractor() as IWatchmanNameInteractor }
    single { PlayerTurnInteractor() as IPlayerTurnInteractor }
    single { PlacedInteractor() as IPlacedInteractor }
    single { ArrivedInteractor() as IArrivedInteractor }

    single { Router() as IRouter }
    single { GetNowScreenInteractor(get()) as IGetNowScreenInteractor }
    single { RouteToStartScreenInteractor(get()) }
    single { RouteToMapScreenIntreactor(get()) }
    single { RouteToSelectPlayerScreenInteractor(get()) }
    single { RouteToGameScreenInteractor(get()) }
    single { OnBackPressedInteractor(get()) }
}