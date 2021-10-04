package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.unit.Guest
import aldtoll.godswars.domain.model.unit.Person
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_person_card.view.*

class PersonsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<Person>()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    var selectedPerson: Person? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return PersonHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_person_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        getItemViewType(holder.itemViewType)
        holder.itemView.run {
            if (item is Guest) {
                personName.text = item.name
            }
            this.setOnClickListener {
                callback.selectPerson(item)
                selectedPerson = item
            }
            val hp = "${item.hp}%"
            personHp.text = hp
            val ap = "AP: ${item.ap}/${item.maxAp}"
            personAp.text = ap
        }
    }

    override fun getItemCount(): Int = items.size

    class PersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun selectPerson(person: Person)
    }
}