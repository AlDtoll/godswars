package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.StarShip
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_room.view.*
import kotlinx.android.synthetic.main.verical_wall_item.view.*

class MapEditorCellsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ROOM = 0
        const val HORIZONTAL = 1
        const val VERTICAL = 2
        const val CROSS = 3
    }

    var starShip = StarShip()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ROOM -> RoomHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
            )
            HORIZONTAL -> HorizontalHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.horizontal_wall_item, parent, false)
            )
            VERTICAL -> VerticalHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.verical_wall_item, parent, false)
            )
            CROSS -> CrossHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.cross_wall_item, parent, false)
            )
            else -> RoomHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val rowNumber = position / (DatabaseInteractor.COLUMN_NUMBER)
        val columnNumber = position % (DatabaseInteractor.COLUMN_NUMBER)
        return when {
            rowNumber % 2 == 0 && columnNumber % 2 == 0 -> ROOM
            rowNumber % 2 == 1 && columnNumber % 2 == 1 -> CROSS
            rowNumber % 2 == 1 && columnNumber % 2 == 0 -> HORIZONTAL
            rowNumber % 2 == 0 && columnNumber % 2 == 1 -> VERTICAL
            else -> ROOM
        }
    }

    override fun getItemCount(): Int = starShip.cells.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = starShip.cells[position]
        when (getItemViewType(position)) {
            ROOM -> {
                holder.itemView.run {
                    this.setOnClickListener {
                        val type = callback.clickCell()
                        if (type == Cell.Type.PIER) {
                            starShip.visit(position)
                        }
                        if (type != Cell.Type.DOOR) {
                            item.type = type
                        }
                        notifyDataSetChanged()
                        callback.saveItems()
                    }
                    room.setBackgroundResource(item.getDrawable())
                }
            }
            //todo возможно надо разделить и назвать не wall а по разному
            VERTICAL, HORIZONTAL -> {
                holder.itemView.run {
                    this.setOnClickListener {
                        val type = callback.clickCell()
                        if (type == Cell.Type.PIER) {
                            starShip.visit(position)
                        }
                        if (type == Cell.Type.DOOR || type == Cell.Type.WALL || type == Cell.Type.ROOM || type == Cell.Type.EMPTY) {
                            item.type = type
                        }
                        notifyDataSetChanged()
                        callback.saveItems()
                    }
                    wall.setBackgroundResource(item.getDrawable())
                }
            }
        }
    }

    class RoomHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class HorizontalHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class VerticalHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class CrossHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun clickCell(): Cell.Type

        fun saveItems()
    }
}