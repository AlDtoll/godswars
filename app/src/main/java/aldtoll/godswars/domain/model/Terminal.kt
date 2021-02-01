package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Terminal(
    val terminal: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_terminal
    }

    override fun getType(): Cell.Type = Cell.Type.TERMINAL
}