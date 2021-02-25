package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.unit.Person
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GameCellsAdapter(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = ArrayList<Cell>()
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

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.run {
            //            this.setOnClickListener {
//                if (guest) {
//                    if (!arrived && items[position] is Pier) {
//                        val piers = items.filter { it.getType() == Cell.Type.PIER }
//                        piers.forEach {
//                            (it as Pier).persons = arrayListOf()
//                        }
//                        (item as Pier).persons = callback.getSelectedGuests()
//                    }
//                    if (arrived) {
//
//                    }
//                } else {
//                    val type = callback.clickCell()
//                    val canPlaceElement = holder.itemViewType == ROOM && items[position] !is Pier
//                    if (canPlaceElement) {
//                        val removedCell = items.removeAt(position)
//                        val newCell = when (type) {
//                            Cell.Type.REACTOR -> {
//                                if (removedCell is Reactor) {
//                                    Room()
//                                } else {
//                                    Reactor()
//                                }
//                            }
//                            Cell.Type.ENGINE -> {
//                                if (removedCell is Engine) {
//                                    Room()
//                                } else {
//                                    Engine()
//                                }
//                            }
//                            Cell.Type.BRIDGE -> {
//                                if (removedCell is Bridge) {
//                                    Room()
//                                } else {
//                                    Bridge()
//                                }
//                            }
//                            Cell.Type.TERMINAL -> {
//                                if (removedCell is Terminal) {
//                                    callback.decreaseCPU()
//                                    Room()
//                                } else {
//                                    callback.increaseCPU()
//                                    Terminal()
//                                }
//                            }
//                            else -> removedCell
//                        }
//                        items.add(position, newCell)
//                    }
//                }
//                notifyDataSetChanged()
//                callback.saveCellsLocal()
//            }
//            if (guest) {
//                if (arrived) {
//                    if (item is Pier) {
//                        backgroundItem.setBackgroundResource(item.getDrawable())
//                    } else if (item.hasPersons()) {
//                        backgroundItem.setBackgroundResource(item.getDrawable())
//                    } else if (getItemViewType(position) != ROOM) {
//                        if (items[position - 1].hasPersons() || items[position + 1].hasPersons()) {
//                            backgroundItem.setBackgroundResource(item.getDrawable())
//                        }
//                    } else {
//                        backgroundItem.setBackgroundResource(R.drawable.ic_shadow)
//                    }
//                } else {
//                    if (item is Pier) {
//                        backgroundItem.setBackgroundResource(item.getDrawable())
//                    } else {
//                        backgroundItem.setBackgroundResource(R.drawable.ic_shadow)
//                    }
//                }
//            } else {
//                backgroundItem.setBackgroundResource(item.getDrawable())
//            }
        }
    }

    class CellHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Callback {

        fun clickCell(): Cell.Type

        fun saveCellsLocal()

        fun increaseCPU()

        fun decreaseCPU()

        fun getSelectedGuests(): java.util.ArrayList<Person>
    }
}