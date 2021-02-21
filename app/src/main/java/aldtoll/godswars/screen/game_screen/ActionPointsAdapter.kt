package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.ActionPoint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActionPointsAdapter(
    private val guest: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<ActionPoint>()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ActionPointHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_action_point,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        getItemViewType(holder.itemViewType)
        holder.itemView.run {
            val color = if (guest) {
                if (item.active) {
                    resources.getColor(R.color.blue_background_color)
                } else {
                    resources.getColor(R.color.light_blue_background_color)
                }
            } else {
                if (item.active) {
                    resources.getColor(R.color.green_background_color)
                } else {
                    resources.getColor(R.color.light_green_background_color)
                }
            }
            this.setBackgroundColor(color)
        }
    }

    override fun getItemCount(): Int = items.size

    class ActionPointHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}