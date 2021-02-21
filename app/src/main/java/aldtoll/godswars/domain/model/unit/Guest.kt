package aldtoll.godswars.domain.model.unit

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Guest(
    var hp: Long = 100,
    var ap: Long = 5
) {
}