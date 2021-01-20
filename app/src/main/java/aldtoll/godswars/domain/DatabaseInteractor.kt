package aldtoll.godswars.domain

import aldtoll.godswars.domain.model.Cell
import aldtoll.godswars.domain.model.Empty
import aldtoll.godswars.domain.model.Room
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
    private val arrivedInteractor: IArrivedInteractor
) : IDatabaseInteractor {

    companion object {
        const val COLUMN_NUMBER = 6
        const val VERTICAL_WALL_NUMBER = COLUMN_NUMBER - 1
        const val ROW_NUMBER = 7
        const val HORIZONTAL_WALL_NUMBER = ROW_NUMBER - 1
    }

    val database = Firebase.database

    override fun clearCells() {
        val myRef = database.getReference("cells")
        val list =
            MutableList((COLUMN_NUMBER + VERTICAL_WALL_NUMBER) * (ROW_NUMBER + HORIZONTAL_WALL_NUMBER)) { index ->
                val rowNumber = index / (COLUMN_NUMBER + VERTICAL_WALL_NUMBER)
                val columnNumber = index % (COLUMN_NUMBER + VERTICAL_WALL_NUMBER)
                if (rowNumber % 2 == 1 && columnNumber % 2 == 1) {
                    Empty()
                } else if (columnNumber % 2 == 1) {
                    Empty()
                } else if (rowNumber % 2 == 1) {
                    Empty()
                } else {
                    Room()
                }
            }
        myRef.setValue(list)
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
    }

    override fun saveGuestName() {
//        val database = Firebase.database
//        val myRef = database.getReference("guestName")
//
//        myRef.setValue(guestNameInteractor.value())
    }

    override fun saveWatchmanName() {
//        val database = Firebase.database
//        val myRef = database.getReference("watchmanName")
//
//        myRef.setValue(watchmanNameInteractor.value())
    }

    override fun clearPlayerName() {
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
    }

    override fun giveTurnToWatchman() {
        val ref = database.getReference("playerTurn")
        ref.setValue(watchmanNameInteractor.value())
    }

    override fun giveTurnToGuest() {
        val ref = database.getReference("playerTurn")
        ref.setValue(guestNameInteractor.value())
    }

    override fun placed() {
        val ref = database.getReference("placed")
        ref.setValue(true)
    }

    override fun arrived() {
        val ref = database.getReference("arrived")
        ref.setValue(true)
    }
}