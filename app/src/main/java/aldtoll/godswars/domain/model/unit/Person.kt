package aldtoll.godswars.domain.model.unit

open class Person(
    open var hp: Long = 100,
    open var maxHp: Long = 100,
    open var ap: Long = 5,
    open var maxAp: Long = 5,
    open var position: Long? = -1
) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Person {
            return Person(
                map["hp"] as Long,
                map["maxHp"] as Long,
                map["ap"] as Long,
                map["maxAp"] as Long,
                map["position"] as Long
            )
        }

        fun nobody(): Person = Person(0, 0, 0, 0, -1)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (hp != other.hp) return false
        if (maxHp != other.maxHp) return false
        if (ap != other.ap) return false
        if (maxAp != other.maxAp) return false
        if (position != other.position) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hp.hashCode()
        result = 31 * result + maxHp.hashCode()
        result = 31 * result + ap.hashCode()
        result = 31 * result + maxAp.hashCode()
        result = 31 * result + (position?.hashCode() ?: 0)
        return result
    }


}