package aldtoll.godswars.domain.model.unit

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Guest(
    val name: String = "ChosenOne",
    override var hp: Long = 100,
    override var maxHp: Long = 100,
    override var ap: Long = 5,
    override var maxAp: Long = 5,
    override var position: Long? = -1
) : Person(hp = hp, maxHp = maxHp, ap = ap, maxAp = maxAp, position = position) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Guest {
            return Guest(
                map["name"] as String,
                map["hp"] as Long,
                map["maxHp"] as Long,
                map["ap"] as Long,
                map["maxAp"] as Long,
                map["position"] as Long
            )
        }
    }
}