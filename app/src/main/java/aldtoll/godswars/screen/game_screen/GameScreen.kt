package aldtoll.godswars.screen.game_screen

import aldtoll.godswars.R
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.Room
import aldtoll.godswars.domain.model.cells.Sheep
import aldtoll.godswars.domain.model.unit.Person
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

    private var roomType: Room.Type = Room.Type.ROOM
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
                showCellsData(it)
            }
        })

        gameScreenViewModel.isGuestData().observe(viewLifecycleOwner, Observer {
            it?.run {
                initActionPoints(it)
                gameCellsAdapter?.guest = it
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

        gameScreenViewModel.currentStatusTextData().observe(viewLifecycleOwner, Observer {
            it?.run {
                gameScreenPlayerTurnStatus.text = it
            }
        })

        gameScreenViewModel.cellsPanelVisibilityData().observe(viewLifecycleOwner, Observer {
            it?.run {
                cellsPanel.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        gameScreenViewModel.isPlacedData().observe(viewLifecycleOwner, Observer {
            it?.run {
                gameCellsAdapter?.placed = it
            }
        })

        gameScreenViewModel.isArrivedData().observe(viewLifecycleOwner, Observer {
            it?.run {
                gameCellsAdapter?.arrived = it
            }
        })

        gameScreenViewModel.actionPointsData().observe(viewLifecycleOwner, Observer {
            showActionPointData(it)
        })

        gameScreenViewModel.personsData().observe(viewLifecycleOwner, Observer {
            showPersons(it)
        })

        createCellsPanel()

        endTurnButton.setOnClickListener {
            gameScreenViewModel.endTurn()
        }

        initMap()
        initPersons()
    }

    private fun createCellsPanel() {
        reactorCell.setOnClickListener {
            colorCell(it)
            roomType = Room.Type.REACTOR
        }
        engineCell.setOnClickListener {
            colorCell(it)
            roomType = Room.Type.ENGINE
        }
        bridgeCell.setOnClickListener {
            colorCell(it)
            roomType = Room.Type.BRIDGE
        }
        terminalCell.setOnClickListener {
            colorCell(it)
            roomType = Room.Type.TERMINAL
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
        override fun clickRoom(): Room.Type {
            return roomType
        }

        override fun saveCellsLocal() {
            gameCellsAdapter?.run {
                gameScreenViewModel.saveCellsLocal(this.sheep.cells)
            }
        }

        override fun increaseCPU() {
            gameScreenViewModel.increaseMaxActionPoints(2)
        }

        override fun decreaseCPU() {
            gameScreenViewModel.decreaseMaxActionPoint(2)
        }

        override fun getSelectedGuests(): ArrayList<Person> {
            return personsAdapter.items
        }
    }
    private var gameCellsAdapter: GameCellsAdapter? = null

    private fun initMap() {
        gameCellsAdapter = GameCellsAdapter(callback)
        cells.adapter = gameCellsAdapter
        context?.run {
            val gridLayoutManager =
                GridLayoutManager(this, DatabaseInteractor.COLUMN_NUMBER)
            cells.layoutManager = gridLayoutManager
        }
    }

    private lateinit var actionPointsAdapter: ActionPointsAdapter

    private fun initActionPoints(isGuest: Boolean) {
        actionPointsAdapter = ActionPointsAdapter(isGuest)
        actionPoints.adapter = actionPointsAdapter
    }

    private fun showCellsData(cells: List<Cell>) {
        val sheep = Sheep()
        sheep.cells = ArrayList(cells)
        gameCellsAdapter?.sheep = sheep
    }

    private fun showActionPointData(actionPoints: List<ActionPoint>) {
        actionPointsAdapter.items = ArrayList(actionPoints)
    }

    private lateinit var personsAdapter: PersonsAdapter

    private fun initPersons() {
        personsAdapter = PersonsAdapter()
        personsList.adapter = personsAdapter
    }

    private fun showPersons(persons: List<Person>) {
        personsAdapter.items = ArrayList(persons)
    }
}