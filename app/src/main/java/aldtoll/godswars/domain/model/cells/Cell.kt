package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.unit.Person
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Cell(
    val position: Long = 0L,
    var type: Type = Type.ROOM,
    var visited: Boolean = false,
    var show: Boolean = false,
    var persons: MutableList<Person>? = null,
    var selected: Boolean = false,
    var enabled: Boolean = false
) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Cell {
            val personsMap = map["persons"]
            val persons = mutableListOf<Person>()
            if (personsMap != null) {
                val personsList = personsMap as ArrayList<HashMap<String, Any>>
                personsList.forEach {
                    persons.add(Person.fromMap(it))
                }
            }
            return Cell(
                map["position"] as Long,
                Type.valueOf(map["type"] as String),
                map["visited"] as Boolean,
                map["show"] as Boolean,
                persons
            )
        }

        fun nothing(): Cell = Cell(400)
    }

    fun getDrawable(): Int {
        return when (type) {
            Type.ROOM -> if (persons.isNullOrEmpty()) {
                R.drawable.ic_room
            } else {
                R.drawable.ic_room_with_alien
            }
            Type.EMPTY -> R.drawable.ic_empty_cell
            Type.PIER -> {
                if (persons.isNullOrEmpty()) {
                    R.drawable.ic_pier_cell
                } else {
                    R.drawable.ic_pier_with_alien
                }
            }
            Type.ENGINE -> R.drawable.ic_engine
            Type.REACTOR -> R.drawable.ic_reactor
            Type.BRIDGE -> R.drawable.ic_servers
            Type.TERMINAL -> R.drawable.ic_terminal
            Type.WALL -> R.drawable.ic_wall
            Type.DOOR -> R.drawable.ic_vertical_door_locked
        }
    }

    fun visit() {
        visited = true
    }

    fun show() {
        show = true
    }

    fun hide() {
        show = false
    }

    fun disable() {
        enabled = false
    }

    enum class Type {
        ROOM,
        EMPTY,
        PIER,
        ENGINE,
        REACTOR,
        BRIDGE,
        TERMINAL,
        WALL,
        DOOR,
    }
}