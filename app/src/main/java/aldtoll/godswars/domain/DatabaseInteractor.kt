package aldtoll.godswars.domain

import aldtoll.godswars.App
import aldtoll.godswars.domain.model.Watchman
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.unit.Guest
import aldtoll.godswars.domain.storage.*
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseInteractor(
    private val cellsListInteractor: ICellsListInteractor,
    private val guestNameInteractor: IGuestNameInteractor,
    private val watchmanNameInteractor: IWatchmanNameInteractor,
    private val playerTurnInteractor: IPlayerTurnInteractor,
    private val placedInteractor: IPlacedInteractor,
    private val arrivedInteractor: IArrivedInteractor,
    private val guestListInteractor: IGuestListInteractor,
    private val watchmanInteractor: IWatchmanInteractor
) : IDatabaseInteractor {

    companion object {
        const val COLUMN_NUMBER = 6
        const val ROW_NUMBER = 7
    }

    private val database = Firebase.database

    override fun clearCells() {
        val myRef = database.getReference("cells")
        val list = mutableListOf<Cell>()
        for (i in 0 until (COLUMN_NUMBER) * (ROW_NUMBER)) {
            list.add(Cell(i.toLong()))
        }
        myRef.setValue(list)
        cellsListInteractor.update(list)
    }

    override fun saveMap() {
        val myRef = database.getReference("cells")
        myRef.setValue(cellsListInteractor.value())
    }

    override fun observeRealtimeDatabase() {
        val myRef = database.getReference("cells")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    if (value is ArrayList<*>) {
                        val arrayList = value as ArrayList<HashMap<String, Any>>
                        val cells = mutableListOf<Cell>()
                        arrayList.forEach {
                            cells.add(Cell.fromMap(it))
                        }
                        cellsListInteractor.update(cells)
                    }
                }
                if (value == null) {
                    clearCells()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        database.getReference("guestName").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    if (value is String) {
                        guestNameInteractor.update(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        database.getReference("watchmanName").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    if (value is String) {
                        watchmanNameInteractor.update(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        database.getReference("playerTurn").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    if (value is String) {
                        playerTurnInteractor.update(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        database.getReference("placed").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    if (value is Boolean) {
                        placedInteractor.update(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        database.getReference("arrived").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    if (value is Boolean) {
                        arrivedInteractor.update(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        database.getReference("guests").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    if (value is ArrayList<*>) {
                        val arrayList = value as ArrayList<HashMap<String, Any>>
                        val guests = mutableListOf<Guest>()
                        arrayList.forEach {
                            guests.add(Guest.fromMap(it))
                        }
                        guestListInteractor.update(guests)
                    }
                }
                if (value == null) {
                    clearGuests()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        database.getReference("watchman").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.value
                value?.run {
                    val hashMap = value as HashMap<String, Any>
                    val watchman = Watchman.fromMap(hashMap)
                    watchmanInteractor.update(watchman)
                }
                if (value == null) {
                    clearWatchman()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    override fun saveGuestName() {
        val database = Firebase.database
        val myRef = database.getReference("guestName")

        myRef.setValue(guestNameInteractor.value())
    }

    override fun saveWatchmanName() {
//        val database = Firebase.database
//        val myRef = database.getReference("watchmanName")
//
//        myRef.setValue(watchmanNameInteractor.value())
    }

    override fun clearPlayerName() {
        val pref = App.getPref()
        pref?.run {
            this.edit().remove("playerName").apply()
        }
        val guestRef = database.getReference("guestName")
        guestRef.setValue("")

        val watchmanRef = database.getReference("watchmanName")
        watchmanRef.setValue("")

        val playerTurnRef = database.getReference("playerTurn")
        playerTurnRef.setValue("")

        val placedRef = database.getReference("placed")
        placedRef.setValue(false)

        val arrivedRef = database.getReference("arrived")
        arrivedRef.setValue(false)

        clearGuests()
        clearWatchman()
    }

    override fun giveTurnToWatchman() {
        val ref = database.getReference("playerTurn")
        ref.setValue(watchmanNameInteractor.value())
    }

    override fun giveTurnToGuest() {
        val ref = database.getReference("playerTurn")
        ref.setValue(guestNameInteractor.value())
    }

    override fun placed(placed: Boolean) {
        val ref = database.getReference("placed")
        ref.setValue(placed)
    }

    override fun arrived(arrived: Boolean) {
        val ref = database.getReference("arrived")
        ref.setValue(arrived)
    }

    override fun saveWatchman() {
        val myRef = database.getReference("watchman")
        myRef.setValue(watchmanInteractor.value())
    }

    private fun clearGuests() {
        val myRef = database.getReference("guests")
        val list = mutableListOf(
            Guest()
        )
        myRef.setValue(list)
        guestListInteractor.update(list)
    }

    private fun clearWatchman() {
        val myRef = database.getReference("watchman")
        val watchman = Watchman()
        myRef.setValue(watchman)
        watchmanInteractor.update(watchman)
    }
}