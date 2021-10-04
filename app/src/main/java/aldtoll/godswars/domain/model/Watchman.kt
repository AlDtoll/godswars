package aldtoll.godswars.domain.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Watchman(
    var cp: Long = 1,
    var maxCp: Long = 1
) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Watchman {
            return Watchman(
                map["cp"] as Long,
                map["maxCp"] as Long
            )
        }
    }
}