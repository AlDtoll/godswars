package aldtoll.godswars.screen.game_screen.guest_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.StarShip
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.screen.map_screen.MapEditorCellsAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_room.view.*
import kotlinx.android.synthetic.main.verical_wall_item.view.*

class GuestsCellsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var starShip = StarShip()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    var arrived = false
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MapEditorCellsAdapter.ROOM -> MapEditorCellsAdapter.RoomHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
            )
            MapEditorCellsAdapter.HORIZONTAL -> MapEditorCellsAdapter.HorizontalHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.horizontal_wall_item, parent, false)
            )
            MapEditorCellsAdapter.VERTICAL -> MapEditorCellsAdapter.VerticalHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.verical_wall_item, parent, false)
            )
            MapEditorCellsAdapter.CROSS -> MapEditorCellsAdapter.CrossHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.cross_wall_item, parent, false)
            )
            else -> MapEditorCellsAdapter.RoomHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val rowNumber = position / (DatabaseInteractor.COLUMN_NUMBER)
        val columnNumber = position % (DatabaseInteractor.COLUMN_NUMBER)
        return when {
            rowNumber % 2 == 0 && columnNumber % 2 == 0 -> MapEditorCellsAdapter.ROOM
            rowNumber % 2 == 1 && columnNumber % 2 == 1 -> MapEditorCellsAdapter.CROSS
            rowNumber % 2 == 1 && columnNumber % 2 == 0 -> MapEditorCellsAdapter.HORIZONTAL
            rowNumber % 2 == 0 && columnNumber % 2 == 1 -> MapEditorCellsAdapter.VERTICAL
            else -> MapEditorCellsAdapter.ROOM
        }
    }

    override fun getItemCount(): Int = starShip.cells.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = starShip.cells[position]
        when (getItemViewType(position)) {
            MapEditorCellsAdapter.ROOM -> {
                holder.itemView.run {
                    this.setOnClickListener {
                        callback.clickCell(item)
                        notifyDataSetChanged()
                        callback.saveCellsLocal()
                    }
                    defineCell(room, roomFog, roomSelect, roomEnabled, item)
                }
            }
            MapEditorCellsAdapter.VERTICAL, MapEditorCellsAdapter.HORIZONTAL -> {
                holder.itemView.run {
//                    this.setOnClickListener {
//                        callback.clickCell(item)
//                        notifyDataSetChanged()
//                        callback.saveCellsLocal()
//                    }
                    defineCell(wall, wallFog, wallSelect, wallEnabled, item)
                }
            }
        }
    }

    private fun defineCell(
        cellElement: View,
        fogElement: View,
        selectElement: View,
        enableElement: View,
        item: Cell
    ) {
        if (item.visited) {
            cellElement.setBackgroundResource(item.getDrawable())
            if (item.show) {
                fogElement.visibility = View.GONE
            } else {
                fogElement.visibility = View.VISIBLE
            }
            if (item.selected) {
                selectElement.visibility = View.VISIBLE
            } else {
                selectElement.visibility = View.GONE
            }
        } else {
            cellElement.setBackgroundResource(R.drawable.ic_shadow)
            fogElement.visibility = View.GONE
        }
        if (item.enabled) {
            enableElement.visibility = View.VISIBLE
        } else {
            enableElement.visibility = View.GONE
        }
    }

    class CellHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun saveCellsLocal()

        fun clickCell(item: Cell)

        fun getSelectedGuests(): ArrayList<Person>
    }
}