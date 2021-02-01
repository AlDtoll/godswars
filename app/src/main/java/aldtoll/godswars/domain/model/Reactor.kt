package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Reactor(
    val reactor: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_reactor
    }

    override fun getType(): Cell.Type = Cell.Type.REACTOR
}