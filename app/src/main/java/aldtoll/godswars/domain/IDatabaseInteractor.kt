package aldtoll.godswars.domain

interface IDatabaseInteractor {

    fun clearCells()

    fun saveMap()

    fun observeRealtimeDatabase()

    fun saveGuestName()

    fun saveWatchmanName()

    fun clearPlayerName()

    fun giveTurnToWatchman()

    fun giveTurnToGuest()

    fun placed()

    fun arrived()

    fun saveWatchman()
}