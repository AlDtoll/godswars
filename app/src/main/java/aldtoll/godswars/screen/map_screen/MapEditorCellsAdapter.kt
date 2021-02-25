package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.Room
import aldtoll.godswars.domain.model.cells.Wall
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cell.view.*

class MapEditorCellsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<Cell>()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return CellHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cell, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.run {
            room.setOnClickListener {
                val type = callback.clickRoom()
                item.room.type = type
                notifyDataSetChanged()
                callback.saveItems()
            }
            rightWall.setOnClickListener {
                val type = callback.clickWall()
                item.rightWall.type = type
                notifyDataSetChanged()
                callback.saveItems()
            }
            bottomWall.setOnClickListener {
                val type = callback.clickWall()
                item.bottomWall.type = type
                notifyDataSetChanged()
                callback.saveItems()
            }
            cross.setOnClickListener {
                val type = callback.clickWall()
                item.cross.type = type
                notifyDataSetChanged()
                callback.saveItems()
            }
            room.setBackgroundResource(item.room.getDrawable())
            rightWall.setBackgroundResource(item.rightWall.getDrawable())
            bottomWall.setBackgroundResource(item.bottomWall.getDrawable())
            cross.setBackgroundResource(item.cross.getDrawable())
        }
    }

    class CellHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun clickRoom(): Room.Type

        fun clickWall(): Wall.Type

        fun saveItems()
    }
}