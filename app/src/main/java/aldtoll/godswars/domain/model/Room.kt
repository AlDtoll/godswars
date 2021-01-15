package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Room(
    var knight: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_room
    }
}