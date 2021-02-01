package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Engine(
    val engine: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_engine
    }

    override fun getType(): Cell.Type = Cell.Type.ENGINE
}