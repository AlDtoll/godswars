package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.domain.model.unit.Person

interface Cell {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Cell {
            return when {
                map.containsKey("knight") -> Room(map["knight"] as Boolean)
                map.containsKey("open") -> Door(
                    map["horizontal"] as Boolean,
                    map["open"] as Boolean
                )
                map.containsKey("empty") -> Empty(map["empty"] as Boolean)
                map.containsKey("pier") -> {
                    val personsMap = map["persons"]
                    var persons = listOf<Person>()
                    if (personsMap != null) {
                        persons = personsMap as List<Person>
                    }
                    Pier(
                        map["pier"] as Boolean,
                        persons
                    )
                }
                map.containsKey("engine") -> Engine(map["engine"] as Boolean)
                map.containsKey("reactor") -> Reactor(map["reactor"] as Boolean)
                map.containsKey("bridge") -> Bridge(map["bridge"] as Boolean)
                map.containsKey("terminal") -> Terminal(map["terminal"] as Boolean)
                else -> Wall(map["exist"] as Boolean)
            }
        }
    }

    fun getDrawable(): Int

    fun getType(): Type

    enum class Type {
        ROOM,
        WALL,
        DOOR,
        EMPTY,
        PIER,
        ENGINE,
        REACTOR,
        BRIDGE,
        TERMINAL
    }
}