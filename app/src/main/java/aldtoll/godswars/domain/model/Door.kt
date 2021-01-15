package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Door(
    val horizontal: Boolean = false,
    val open: Boolean = false
) : Cell {


    override fun getDrawable(): Int {
        return if (horizontal) R.drawable.ic_horizontal_door_locked else R.drawable.ic_vertical_door_locked
    }

}