package aldtoll.godswars.domain.model.unit

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Guest(
    val name: String = "ChosenOne",
    override var hp: Long = 100,
    override var ap: Long = 5
) : Person(hp = hp, ap = ap) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Guest {
            return Guest(
                map["name"] as String,
                map["hp"] as Long,
                map["ap"] as Long
            )
        }
    }
}