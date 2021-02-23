package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.unit.Person
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Pier(
    val pier: Boolean = false,
    var persons: List<Person>? = null
) : Cell {

    override fun getDrawable(): Int {
        return if (persons?.isNotEmpty() == true) R.drawable.ic_pier_with_alien else R.drawable.ic_pier_cell
    }

    override fun getType(): Cell.Type =
        Cell.Type.PIER
}