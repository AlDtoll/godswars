package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.cells.Cell
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
    private var type: Cell.Type = Cell.Type.ROOM

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
            type = Cell.Type.ROOM
        }
        doorCell.setOnClickListener {
            type = Cell.Type.DOOR
        }
        emptyCell.setOnClickListener {
            type = Cell.Type.EMPTY
        }
        wallCell.setOnClickListener {
            type = Cell.Type.WALL
        }
        pierCell.setOnClickListener {
            type = Cell.Type.PIER
        }

        initRecyclerView()
    }

    private val callback = object : MapEditorCellsAdapter.Callback {
        override fun clickCell(): Cell.Type {
            return type
        }

        override fun saveItems() {
            mapEditorScreenViewModel.saveCells(mapEditorCellsAdapter.items)
        }

    }
    private lateinit var mapEditorCellsAdapter: MapEditorCellsAdapter


    private fun initRecyclerView() {
        val numberOfCellsAndWalls =
            DatabaseInteractor.COLUMN_NUMBER + DatabaseInteractor.VERTICAL_WALL_NUMBER
        mapEditorCellsAdapter = MapEditorCellsAdapter(callback, numberOfCellsAndWalls)
        cells.adapter = mapEditorCellsAdapter
        val value = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val columnNumber = position % numberOfCellsAndWalls
                return when (columnNumber % 2) {
                    1 -> 1
                    else -> 2
                }
            }
        }
        context?.run {
            val gridLayoutManager =
                GridLayoutManager(this, numberOfCellsAndWalls + DatabaseInteractor.COLUMN_NUMBER)
            gridLayoutManager.spanSizeLookup = value
            cells.layoutManager = gridLayoutManager
        }
    }

    private fun showData(cells: List<Cell>) {
        mapEditorCellsAdapter.items = ArrayList(cells)
    }

}