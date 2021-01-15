package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.model.Cell
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*

class GameScreen : Fragment() {

    companion object {
        fun newInstance(): GameScreen =
            GameScreen()
    }

    private var isGuest = false
    private var type: Cell.Type = Cell.Type.ROOM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        reactorCell.setOnClickListener {
            type = Cell.Type.REACTOR
        }
        engineCell.setOnClickListener {
            type = Cell.Type.ENGINE
        }
        bridgeCell.setOnClickListener {
            type = Cell.Type.BRIDGE
        }
        terminalCell.setOnClickListener {
            type = Cell.Type.TERMINAL
        }

//        initRecyclerView()
    }

//    private fun initRecyclerView() {
//        val numberOfCellsAndWalls =
//            DatabaseInteractor.COLUMN_NUMBER + DatabaseInteractor.VERTICAL_WALL_NUMBER
//        mapEditorCellsAdapter = MapEditorCellsAdapter(callback, numberOfCellsAndWalls)
//        cells.adapter = mapEditorCellsAdapter
//        val value = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                val columnNumber = position % numberOfCellsAndWalls
//                return when (columnNumber % 2) {
//                    1 -> 1
//                    else -> 2
//                }
//            }
//        }
//        context?.run {
//            val gridLayoutManager =
//                GridLayoutManager(this, numberOfCellsAndWalls + DatabaseInteractor.COLUMN_NUMBER)
//            gridLayoutManager.spanSizeLookup = value
//            cells.layoutManager = gridLayoutManager
//        }
//    }
//
//    private fun showData(cells: List<Cell>) {
//        mapEditorCellsAdapter.items = ArrayList(cells)
//    }
}