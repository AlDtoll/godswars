package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.Cell
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_game.*
import org.koin.android.ext.android.inject

class GameScreen : Fragment() {

    companion object {
        fun newInstance(): GameScreen =
            GameScreen()
    }

    private var isGuest = false
    private var isPlaced = false
    private var isArrived = false
    private var type: Cell.Type = Cell.Type.ROOM
    private val gameScreenViewModel: IGameScreenViewModel by inject()

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
        gameScreenViewModel.cellsData().observe(viewLifecycleOwner, Observer {
            it?.run {
                showData(it)
            }
        })

        gameScreenViewModel.isGuestData().observe(viewLifecycleOwner, Observer {
            it?.run {
                isGuest = it
            }
        })

        gameScreenViewModel.enableTurnButtonData().observe(viewLifecycleOwner, Observer {
            it?.run {
                endTurnButton.isEnabled = it
            }
        })

        gameScreenViewModel.turnButtonTextData().observe(viewLifecycleOwner, Observer {
            it?.run {
                endTurnButton.text = it
            }
        })

        gameScreenViewModel.cellsPanelVisibilityData().observe(viewLifecycleOwner, Observer {
            it?.run {
                cellsPanel.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        gameScreenViewModel.isPlacedData().observe(viewLifecycleOwner, Observer {
            it?.run {
                isPlaced = it
            }
        })

        gameScreenViewModel.isArrivedData().observe(viewLifecycleOwner, Observer {
            it?.run {
                isArrived = it
            }
        })

        createCellsPanel()

        endTurnButton.setOnClickListener {
            gameScreenViewModel.endTurn()
        }

        initRecyclerView()
    }

    private fun createCellsPanel() {
        reactorCell.setOnClickListener {
            colorCell(it)
            type = Cell.Type.REACTOR
        }
        engineCell.setOnClickListener {
            colorCell(it)
            type = Cell.Type.ENGINE
        }
        bridgeCell.setOnClickListener {
            colorCell(it)
            type = Cell.Type.BRIDGE
        }
        terminalCell.setOnClickListener {
            colorCell(it)
            type = Cell.Type.TERMINAL
        }
    }

    private fun colorCell(panelCell: View) {
        reactorCell.setBackgroundColor(resources.getColor(android.R.color.white))
        engineCell.setBackgroundColor(resources.getColor(android.R.color.white))
        bridgeCell.setBackgroundColor(resources.getColor(android.R.color.white))
        terminalCell.setBackgroundColor(resources.getColor(android.R.color.white))
        panelCell.setBackgroundColor(resources.getColor(R.color.select_color))
    }

    private val callback = object : GameCellsAdapter.Callback {
        override fun clickCell(): Cell.Type {
            return type
        }

        override fun saveItems() {
            gameScreenViewModel.saveCells(gameCellsAdapter.items)
        }

    }
    private lateinit var gameCellsAdapter: GameCellsAdapter

    private fun initRecyclerView() {
        val numberOfCellsAndWalls =
            DatabaseInteractor.COLUMN_NUMBER + DatabaseInteractor.VERTICAL_WALL_NUMBER
        gameCellsAdapter = GameCellsAdapter(callback, numberOfCellsAndWalls)
        cells.adapter = gameCellsAdapter
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
        gameCellsAdapter.items = ArrayList(cells)
    }
}