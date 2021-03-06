package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Wall(
    val horizontal: Boolean = false,
    var type: Type = Type.EMPTY,
    var visited: Boolean = false,
    var show: Boolean = false,
    var selected: Boolean = false,
    var enabled: Boolean = false
) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Wall {
            return Wall(
                map["horizontal"] as Boolean,
                Type.valueOf(map["type"] as String),
                map["visited"] as Boolean,
                map["show"] as Boolean
            )
        }
    }

    fun getDrawable(): Int {
        return when (type) {
            Type.WALL -> R.drawable.ic_wall
            Type.DOOR -> {
                if (horizontal) {
                    R.drawable.ic_horizontal_door_locked
                } else {
                    R.drawable.ic_vertical_door_locked
                }
            }
            Type.EMPTY -> R.drawable.ic_empty_cell
            else -> R.drawable.ic_empty_cell
        }
    }

    enum class Type {
        WALL,
        DOOR,
        EMPTY,
        NO
    }

}