package aldtoll.godswars.domain.model

import aldtoll.godswars.R
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Bridge(
    val bridge: Boolean = false
) : Cell {

    override fun getDrawable(): Int {
        return R.drawable.ic_servers
    }
}