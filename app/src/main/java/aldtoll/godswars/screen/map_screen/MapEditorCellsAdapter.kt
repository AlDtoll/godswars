package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.cells.Room
import aldtoll.godswars.domain.model.cells.Sheep
import aldtoll.godswars.domain.model.cells.Wall
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cell.view.*

class MapEditorCellsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sheep = Sheep()
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

    override fun getItemCount(): Int = sheep.cells.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = sheep.cells[position]
        holder.itemView.run {
            room.setOnClickListener {
                val type = callback.clickRoom()
                item.room.type = type
                if (type == Room.Type.PIER) {
                    sheep.visit(position)
                }
                notifyDataSetChanged()
                callback.saveItems()
            }
            rightWall.setOnClickListener {
                val type = callback.clickWall()
                item.rightWall.type = type
                if (position % DatabaseInteractor.COLUMN_NUMBER != DatabaseInteractor.COLUMN_NUMBER - 1) {
                    sheep.cells[position + 1].leftWall.type = type
                }
                notifyDataSetChanged()
                callback.saveItems()
            }
            bottomWall.setOnClickListener {
                val type = callback.clickWall()
                item.bottomWall.type = type
                if (position + DatabaseInteractor.COLUMN_NUMBER < sheep.cells.size) {
                    sheep.cells[position + DatabaseInteractor.COLUMN_NUMBER].upWall.type = type
                }
                notifyDataSetChanged()
                callback.saveItems()
            }
//            cross.setOnClickListener {
//                val type = callback.clickWall()
//                if (type != Wall.Type.DOOR) {
//                    item.cross.type = type
//                    notifyDataSetChanged()
//                    callback.saveItems()
//                }
//            }
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