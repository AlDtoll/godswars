package aldtoll.godswars.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ActionPoint(
    val active: Boolean = false
)