package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.unit.Person
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Room(
    var visited: Boolean = false,
    var show: Boolean = false,
    var persons: List<Person>? = null,
    var type: Type = Type.ROOM
) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Room {
            val personsMap = map["persons"]
            var persons = listOf<Person>()
            if (personsMap != null) {
                persons = personsMap as List<Person>
            }
            return Room(
                map["visited"] as Boolean,
                map["show"] as Boolean,
                persons,
                Type.valueOf(map["type"] as String)
            )
        }
    }

    fun getDrawable(): Int {
        return when (type) {
            Type.ROOM -> R.drawable.ic_room
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
        }
    }

    enum class Type {
        ROOM,
        EMPTY,
        PIER,
        ENGINE,
        REACTOR,
        BRIDGE,
        TERMINAL
    }
}