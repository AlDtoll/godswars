package aldtoll.godswars.screen.game_screen.guest_screen

import aldtoll.godswars.databinding.FragmentGuestsBinding
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
import org.koin.android.ext.android.inject

class GuestsScreen : Fragment() {

    companion object {
        fun newInstance(): GuestsScreen =
            GuestsScreen()
    }

    private val guestsScreenViewModel: IGuestsScreenViewModel by inject()
    private lateinit var binding: FragmentGuestsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!::binding.isInitialized) {
            binding = FragmentGuestsBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        guestsScreenViewModel.cellsData().observe(viewLifecycleOwner, {
            it?.run {
                showCellsData(it)
            }
        })

        initActionPoints()

        guestsScreenViewModel.enableTurnButtonData().observe(viewLifecycleOwner, {
            it?.run {
                binding.guestsScreenEndTurnButton.isEnabled = it
            }
        })

        guestsScreenViewModel.turnButtonTextData().observe(viewLifecycleOwner, {
            it?.run {
                binding.guestsScreenEndTurnButton.text = it
            }
        })

        guestsScreenViewModel.currentStatusTextData().observe(viewLifecycleOwner, {
            it?.run {
                binding.guestsScreenPlayerTurnStatus.text = it
            }
        })

        guestsScreenViewModel.isArrivedData().observe(viewLifecycleOwner, {
            it?.run {
                guestsCellsAdapter?.arrived = it
            }
        })

        guestsScreenViewModel.actionPointsData().observe(viewLifecycleOwner, {
            showActionPointData(it)
        })

        guestsScreenViewModel.personsData().observe(viewLifecycleOwner, {
            showPersons(it)
        })

        guestsScreenViewModel.selectedPersonData().observe(viewLifecycleOwner, {
            it?.run {
                if (it != Person.nobody()) {
                    showPerson(it)
                }
            }
        })

        binding.guestsScreenEndTurnButton.setOnClickListener {
            guestsScreenViewModel.endTurn()
        }

        initMap()
        initPersons()
    }

    private val guestsCellCallback = object : GuestsCellsAdapter.Callback {

        override fun saveCellsLocal() {
            guestsCellsAdapter?.run {
                guestsScreenViewModel.saveCellsLocal(this.starShip.cells)
            }
        }

        override fun clickCell(item: Cell) {
            guestsScreenViewModel.clickCell(item)
        }

        override fun getSelectedGuests(): ArrayList<Person> {
            return personsAdapter.items
        }
    }
    private var guestsCellsAdapter: GuestsCellsAdapter? = null

    private fun initMap() {
        guestsCellsAdapter = GuestsCellsAdapter(guestsCellCallback)
        binding.guestsScreenCells.adapter = guestsCellsAdapter
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
            binding.guestsScreenCells.layoutManager = gridLayoutManager
        }
    }

    private lateinit var actionPointsAdapter: ActionPointsAdapter

    private fun initActionPoints() {
        actionPointsAdapter = ActionPointsAdapter(true)
        binding.guestsScreenActionPoints.adapter = actionPointsAdapter
    }

    private fun showCellsData(cells: List<Cell>) {
        val starShip = StarShip()
        starShip.cells = ArrayList(cells)
        guestsCellsAdapter?.starShip = starShip
    }

    private fun showActionPointData(actionPoints: List<ActionPoint>) {
        actionPointsAdapter.items = ArrayList(actionPoints)
    }

    private lateinit var personsAdapter: PersonsAdapter
    private val personCallback = object : PersonsAdapter.Callback {

        override fun selectPerson(person: Person) {
            guestsScreenViewModel.selectPerson(person)
            guestsCellsAdapter?.selectedPerson = person
        }
    }

    private fun initPersons() {
        personsAdapter = PersonsAdapter(personCallback)
        binding.guestsScreenPersonsList.adapter = personsAdapter
    }

    private fun showPersons(persons: List<Person>) {
        personsAdapter.items = ArrayList(persons)
    }

    private fun showPerson(person: Person) {
        binding.guestsScreenPersonCard.root.visibility = View.VISIBLE
        binding.guestsScreenPersonCard.root.setOnClickListener {
            binding.guestsScreenPersonCard.root.visibility = View.GONE
            guestsScreenViewModel.selectPerson(Person.nobody())
        }
        if (person is Guest) {
            binding.guestsScreenPersonCard.personName.text = person.name
        }
        val hp = "${person.hp}%"
        binding.guestsScreenPersonCard.personHp.text = hp
        val ap = "AP: ${person.ap}/${person.maxAp}"
        binding.guestsScreenPersonCard.personAp.text = ap
    }
}