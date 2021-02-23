package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Room(
    var knight: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_room
    }

    override fun getType(): Cell.Type =
        Cell.Type.ROOM
}