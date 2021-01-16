package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cell.view.*

class GameCellsAdapter(
    private val callback: Callback,
    private val numberOfCellsAndWalls: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ROOM = 0
        private const val VERTICAL_WALL = 1
        private const val HORIZONTAL_WALL = 2
        private const val CROSS_WALL = 3
    }

    var items = ArrayList<Cell>()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when (p1) {
            ROOM -> CellHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cell, parent, false)
            )
            VERTICAL_WALL -> CellHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.verical_wall_item, parent, false)
            )
            HORIZONTAL_WALL -> CellHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.horizontal_wall_item, parent, false)
            )
            CROSS_WALL -> CellHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cross_wall_item, parent, false)
            )
            else -> CellHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cell, parent, false)
            )
        }

    }

    override fun getItemViewType(position: Int): Int {
        val rowNumber = position / numberOfCellsAndWalls
        val columnNumber = position % numberOfCellsAndWalls
        return if (rowNumber % 2 == 1 && columnNumber % 2 == 1) {
            CROSS_WALL
        } else if (columnNumber % 2 == 1) {
            VERTICAL_WALL
        } else if (rowNumber % 2 == 1) {
            HORIZONTAL_WALL
        } else {
            ROOM
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        getItemViewType(holder.itemViewType)
        holder.itemView.run {
            this.setOnClickListener {
                val type = callback.clickCell()
                val canPlaceElement = holder.itemViewType == ROOM && items[position] !is Pier
                if (canPlaceElement) {
                    val removeAt = items.removeAt(position)
                    val newCell = when (type) {
                        Cell.Type.REACTOR -> Reactor()
                        Cell.Type.ENGINE -> Engine()
                        Cell.Type.BRIDGE -> Bridge()
                        Cell.Type.TERMINAL -> Terminal()
                        else -> removeAt
                    }
                    items.add(position, newCell)
                    notifyDataSetChanged()
                    callback.saveItems()
                }
            }
            backgroundItem.setBackgroundResource(item.getDrawable())
        }
    }

    class CellHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun clickCell(): Cell.Type

        fun saveItems()
    }
}