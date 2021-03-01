package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.cells.Room
import aldtoll.godswars.domain.model.cells.Sheep
import aldtoll.godswars.domain.model.cells.Wall
import aldtoll.godswars.domain.model.unit.Person
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cell.view.*

class GameCellsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sheep = Sheep()
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    var placed = false
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    var arrived = false
        set(data) {
            field = data
            notifyDataSetChanged()
        }

    var guest = false
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
            this.setOnClickListener {
                if (guest) {
                    if (!arrived) {
                        if (item.room.type == Room.Type.PIER) {
                            val selectedGuests = callback.getSelectedGuests()
                            if (item.room.persons == selectedGuests) {
                                item.room.persons = arrayListOf()
                                sheep.hide(position)
                            } else {
                                val piers = sheep.cells.filter { it.room.type == Room.Type.PIER }
                                piers.forEach {
                                    it.room.persons = arrayListOf()
                                    sheep.hide(it.position.toInt())
                                }
                                item.room.persons = selectedGuests
                                sheep.show(position)
                            }
                        }
                    } else {

                    }
                } else {
                    val type = callback.clickRoom()
                    val canPlaceElement = sheep.cells[position].room.type != Room.Type.PIER
                    if (canPlaceElement) {
                        if (item.room.type == type) {
                            item.room.type = Room.Type.ROOM
                        } else {
                            item.room.type = type
                        }
                    }
                }
                notifyDataSetChanged()
                callback.saveCellsLocal()
            }
            if (guest) {
                defineCellRoom(room, roomFog, item.room)
                defineCellWall(rightWall, rightWallFog, item.rightWall)
                defineCellWall(bottomWall, bottomWallFog, item.bottomWall)
                defineCellWall(cross, crossFog, item.cross)
            } else {
                room.setBackgroundResource(item.room.getDrawable())
                rightWall.setBackgroundResource(item.rightWall.getDrawable())
                bottomWall.setBackgroundResource(item.bottomWall.getDrawable())
                cross.setBackgroundResource(item.cross.getDrawable())
            }
        }
    }

    private fun defineCellRoom(
        cellElement: View,
        fogElement: View,
        item: Room
    ) {
        if (item.visited) {
            cellElement.setBackgroundResource(item.getDrawable())
            if (item.show) {
                fogElement.visibility = View.GONE
            } else {
                fogElement.visibility = View.VISIBLE
            }
        } else {
            cellElement.setBackgroundResource(R.drawable.ic_shadow)
            fogElement.visibility = View.GONE
        }
    }

    private fun defineCellWall(
        cellElement: View,
        fogElement: View,
        item: Wall
    ) {
        if (item.visited) {
            cellElement.setBackgroundResource(item.getDrawable())
            if (item.show) {
                fogElement.visibility = View.GONE
            } else {
                fogElement.visibility = View.VISIBLE
            }
        } else {
            cellElement.setBackgroundResource(R.drawable.ic_shadow)
            fogElement.visibility = View.GONE
        }
    }

    class CellHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun clickRoom(): Room.Type

        fun saveCellsLocal()

        fun increaseCPU()

        fun decreaseCPU()

        fun getSelectedGuests(): ArrayList<Person>
    }
}