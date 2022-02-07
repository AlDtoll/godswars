package aldtoll.godswars.screen.game_screen.watchman_screen

import aldtoll.godswars.R
import aldtoll.godswars.databinding.FragmentWatchmanBinding
import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.model.ActionPoint
import aldtoll.godswars.domain.model.cells.Cell
import aldtoll.godswars.domain.model.cells.StarShip
import aldtoll.godswars.domain.model.unit.Guest
import aldtoll.godswars.domain.model.unit.Person
import aldtoll.godswars.screen.game_screen.ActionPointsAdapter
import aldtoll.godswars.screen.game_screen.PersonsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_watchman.*
import org.koin.android.ext.android.inject

class WatchmanScreen : Fragment() {

    companion object {
        fun newInstance(): WatchmanScreen =
            WatchmanScreen()
    }

    private var cellType: Cell.Type = Cell.Type.ROOM
    private val watchmanScreenViewModel: IWatchmanScreenViewModel by inject()
    private lateinit var binding: FragmentWatchmanBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentWatchmanBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        watchmanScreenViewModel.cellsData().observe(viewLifecycleOwner) {
            it?.run {
                showCellsData(it)
            }
        }

        initActionPoints()

        watchmanScreenViewModel.enableTurnButtonData().observe(viewLifecycleOwner) {
            it?.run {
                binding.watchmanScreenEndTurnButton.isEnabled = it
            }
        }

        watchmanScreenViewModel.turnButtonTextData().observe(viewLifecycleOwner) {
            it?.run {
                binding.watchmanScreenEndTurnButton.text = it
            }
        }

        watchmanScreenViewModel.currentStatusTextData().observe(viewLifecycleOwner) {
            it?.run {
                binding.watchmanScreenPlayerTurnStatus.text = it
            }
        }

        watchmanScreenViewModel.cellsPanelVisibilityData().observe(viewLifecycleOwner) {
            it?.run {
                binding.watchmanScreenCellsPanel.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        watchmanScreenViewModel.isPlacedData().observe(viewLifecycleOwner) {
            it?.run {
                watchmanCellsAdapter?.placed = it
            }
        }

        watchmanScreenViewModel.actionPointsData().observe(viewLifecycleOwner) {
            showActionPointData(it)
        }

        watchmanScreenViewModel.personsData().observe(viewLifecycleOwner) {
            showPersons(it)
        }

        watchmanScreenViewModel.selectedPersonData().observe(viewLifecycleOwner) {
            it?.run {
                if (it != Person.nobody()) {
                    showPerson(it)
                }
            }
        }

        createCellsPanel()

        binding.watchmanScreenEndTurnButton.setOnClickListener {
            watchmanScreenViewModel.endTurn()
        }

        initMap()
        initPersons()
    }

    private fun createCellsPanel() {
        binding.reactorCell.setOnClickListener {
            colorCell(it)
            cellType = Cell.Type.REACTOR
        }
        binding.engineCell.setOnClickListener {
            colorCell(it)
            cellType = Cell.Type.ENGINE
        }
        binding.bridgeCell.setOnClickListener {
            colorCell(it)
            cellType = Cell.Type.BRIDGE
        }
        binding.terminalCell.setOnClickListener {
            colorCell(it)
            cellType = Cell.Type.TERMINAL
        }
    }

    private fun colorCell(panelCell: View) {
        binding.reactorCell.setBackgroundColor(resources.getColor(android.R.color.white))
        binding.engineCell.setBackgroundColor(resources.getColor(android.R.color.white))
        binding.bridgeCell.setBackgroundColor(resources.getColor(android.R.color.white))
        binding.terminalCell.setBackgroundColor(resources.getColor(android.R.color.white))
        panelCell.setBackgroundColor(resources.getColor(R.color.select_color))
    }

    private val watchmanCellCallback = object : WatchmanCellsAdapter.Callback {
        override fun clickRoom(): Cell.Type {
            return cellType
        }

        override fun saveCellsLocal() {
            watchmanCellsAdapter?.run {
                watchmanScreenViewModel.saveCellsLocal(this.starShip.cells)
            }
        }

        override fun increaseCPU() {
            watchmanScreenViewModel.increaseMaxActionPoints(2)
        }

        override fun decreaseCPU() {
            watchmanScreenViewModel.decreaseMaxActionPoint(2)
        }

        override fun clickCell(item: Cell) {
            watchmanScreenViewModel.clickCell(item)
        }
    }
    private var watchmanCellsAdapter: WatchmanCellsAdapter? = null

    private fun initMap() {
        watchmanCellsAdapter = WatchmanCellsAdapter(watchmanCellCallback)
        watchmanScreenCells.adapter = watchmanCellsAdapter
        context?.run {
            val gridLayoutManager =
                GridLayoutManager(this, DatabaseInteractor.COLUMN_NUMBER)
            watchmanScreenCells.layoutManager = gridLayoutManager
        }
    }

    private lateinit var actionPointsAdapter: ActionPointsAdapter

    private fun initActionPoints() {
        actionPointsAdapter = ActionPointsAdapter(false)
        watchmanScreenActionPoints.adapter = actionPointsAdapter
    }

    private fun showCellsData(cells: List<Cell>) {
        val starShip = StarShip()
        starShip.cells = ArrayList(cells)
        watchmanCellsAdapter?.starShip = starShip
    }

    private fun showActionPointData(actionPoints: List<ActionPoint>) {
        actionPointsAdapter.items = ArrayList(actionPoints)
    }

    private lateinit var personsAdapter: PersonsAdapter
    private val personCallback = object : PersonsAdapter.Callback {

        override fun selectPerson(person: Person) {
            watchmanScreenViewModel.selectPerson(person)
            watchmanCellsAdapter?.selectedPerson = person
        }
    }

    private fun initPersons() {
        personsAdapter = PersonsAdapter(personCallback)
        binding.watchmanScreenPersonsList.adapter = personsAdapter
    }

    private fun showPersons(persons: List<Person>) {
        personsAdapter.items = ArrayList(persons)
    }

    private fun showPerson(person: Person) {
        binding.watchmanSelectedPersonCard.root.visibility = View.VISIBLE
        binding.watchmanSelectedPersonCard.root.setOnClickListener {
            binding.watchmanSelectedPersonCard.root.visibility = View.GONE
            watchmanScreenViewModel.selectPerson(Person.nobody())
        }
        if (person is Guest) {
            binding.watchmanSelectedPersonCard.personName.text = person.name
        }
        val hp = "${person.hp}%"
        binding.watchmanSelectedPersonCard.personHp.text = hp
        val ap = "AP: ${person.ap}/${person.maxAp}"
        binding.watchmanSelectedPersonCard.personAp.text = ap
    }
}