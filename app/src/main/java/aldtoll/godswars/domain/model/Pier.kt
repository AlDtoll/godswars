package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Pier(
    val pier: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_pier_cell
    }
}