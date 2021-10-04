package aldtoll.godswars.screen.game_screen.watchman_screen

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
import kotlinx.android.synthetic.main.item_cell.view.*

class WatchmanCellsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var starShip = StarShip()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    var placed = false
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    var selectedPerson: Person? = null

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
        holder.itemView.run {
            roomSelect.visibility = View.GONE
            this.setOnClickListener {
                val type = callback.clickRoom()
                val canPlaceElement = starShip.cells[position].type != Cell.Type.PIER
                if (canPlaceElement) {
                    if (item.type == type) {
                        item.type = Cell.Type.ROOM
                    } else {
                        item.type = type
                    }
                }
                notifyDataSetChanged()
                callback.saveCellsLocal()
            }
            //todo для переопределить для стен и комнат
            room.setBackgroundResource(item.getDrawable())
        }
    }

    class CellHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun clickRoom(): Cell.Type

        fun saveCellsLocal()

        fun increaseCPU()

        fun decreaseCPU()

        fun clickCell(item: Cell)
    }
}