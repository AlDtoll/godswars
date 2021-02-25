package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.Room
import aldtoll.godswars.domain.model.cells.Wall
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_map_editor.*
import org.koin.android.ext.android.inject


class MapEditorScreen : Fragment() {

    private val mapEditorScreenViewModel: IMapEditorScreenViewModel by inject()
    private var roomType: Room.Type = Room.Type.ROOM
    private var wallType: Wall.Type = Wall.Type.EMPTY

    companion object {
        fun newInstance(): MapEditorScreen =
            MapEditorScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        mapEditorScreenViewModel.cellsData().observe(viewLifecycleOwner, Observer {
            it?.run {
                showData(it)
            }
        })

        roomCell.setOnClickListener {
            roomType = Room.Type.ROOM
        }
        doorCell.setOnClickListener {
            wallType = Wall.Type.DOOR
        }
        emptyCell.setOnClickListener {
            wallType = Wall.Type.EMPTY
            roomType = Room.Type.EMPTY
        }
        wallCell.setOnClickListener {
            wallType = Wall.Type.WALL
        }
        pierCell.setOnClickListener {
            roomType = Room.Type.PIER
        }

        initRecyclerView()
    }

    private val callback = object : MapEditorCellsAdapter.Callback {
        override fun clickRoom(): Room.Type {
            return roomType
        }

        override fun clickWall(): Wall.Type {
            return wallType
        }

        override fun saveItems() {
            mapEditorScreenViewModel.saveCells(mapEditorCellsAdapter.items)
        }

    }
    private lateinit var mapEditorCellsAdapter: MapEditorCellsAdapter


    private fun initRecyclerView() {
        mapEditorCellsAdapter = MapEditorCellsAdapter(callback)
        cells.adapter = mapEditorCellsAdapter
        context?.run {
            val gridLayoutManager =
                GridLayoutManager(this, DatabaseInteractor.COLUMN_NUMBER)
            cells.layoutManager = gridLayoutManager
        }
    }

    private fun showData(cells: List<Cell>) {
        mapEditorCellsAdapter.items = ArrayList(cells)
    }

}