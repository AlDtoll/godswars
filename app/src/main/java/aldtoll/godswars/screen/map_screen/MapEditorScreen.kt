package aldtoll.godswars.screen.map_screen

import aldtoll.godswars.databinding.FragmentMapEditorBinding
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.StarShip
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.ext.android.inject


class MapEditorScreen : Fragment() {

    private lateinit var binding: FragmentMapEditorBinding
    private val mapEditorScreenViewModel: IMapEditorScreenViewModel by inject()
    private var cellType: Cell.Type = Cell.Type.ROOM

    companion object {
        fun newInstance(): MapEditorScreen =
            MapEditorScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentMapEditorBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        mapEditorScreenViewModel.cellsData().observe(viewLifecycleOwner) {
            it?.run {
                showData(it)
            }
        }

        binding.roomCell.setOnClickListener {
            cellType = Cell.Type.ROOM
        }
        binding.doorCell.setOnClickListener {
            cellType = Cell.Type.DOOR
        }
        binding.emptyCell.setOnClickListener {
            cellType = Cell.Type.EMPTY
        }
        binding.wallCell.setOnClickListener {
            cellType = Cell.Type.WALL
        }
        binding.pierCell.setOnClickListener {
            cellType = Cell.Type.PIER
        }

        initMapForEdit()
    }

    private val callback = object : MapEditorCellsAdapter.Callback {
        override fun clickCell(): Cell.Type {
            return cellType
        }

        override fun saveItems() {
            mapEditorScreenViewModel.saveCells(mapEditorCellsAdapter.starShip.cells)
        }

    }
    private lateinit var mapEditorCellsAdapter: MapEditorCellsAdapter


    private fun initMapForEdit() {
        mapEditorCellsAdapter = MapEditorCellsAdapter(callback)
        binding.cells.adapter = mapEditorCellsAdapter
        val value = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val columnNumber = position % DatabaseInteractor.COLUMN_NUMBER
                return when (columnNumber % 2) {
                    1 -> 1
                    else -> 2
                }
            }
        }
        context?.run {
            val gridLayoutManager =
                GridLayoutManager(
                    this,
                    DatabaseInteractor.COLUMN_NUMBER + DatabaseInteractor.ROOM_COLUMN_NUMBER
                )
            gridLayoutManager.spanSizeLookup = value
            binding.cells.layoutManager = gridLayoutManager
        }
    }

    private fun showData(cells: List<Cell>) {
        val starShip = StarShip()
        starShip.cells = ArrayList(cells)
        mapEditorCellsAdapter.starShip = starShip
    }

}