package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Wall(
    val exist: Boolean = false
) : Cell {


    override fun getDrawable(): Int {
        return R.drawable.ic_wall
    }

    override fun getType(): Cell.Type =
        Cell.Type.WALL

}