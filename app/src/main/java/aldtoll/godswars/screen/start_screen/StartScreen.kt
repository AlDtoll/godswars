package aldtoll.godswars.screen.start_screen

import aldtoll.godswars.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.android.ext.android.inject

class StartScreen : Fragment() {

    companion object {
        fun newInstance(): StartScreen =
            StartScreen()
    }

    private val startScreenViewModel: IStartScreenViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        startGameButton.setOnClickListener {
            startScreenViewModel.startGameClicked()
        }
        createMapButton.setOnClickListener {
            startScreenViewModel.createMapClicked()
        }
    }

}