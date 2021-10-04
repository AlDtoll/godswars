package aldtoll.godswars.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ActionPoint(
    var active: Boolean = false
)