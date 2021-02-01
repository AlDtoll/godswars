package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Empty(
    val empty: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_empty_cell
    }

    override fun getType(): Cell.Type = Cell.Type.EMPTY
}