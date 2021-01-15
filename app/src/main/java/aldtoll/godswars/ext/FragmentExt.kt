package aldtoll.godswars.ext

import aldtoll.godswars.R
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragment(
    fragment: Fragment, @IdRes containerId: Int = R.id.container,
    tag: String = fragment::class.java.name,
    backStack: Boolean = false,
    replaceFragments: Boolean = true
) {
    try {
        supportFragmentManager.beginTransaction().apply {
            if (replaceFragments) replace(containerId, fragment, tag)
            else add(containerId, fragment, tag)

            if (backStack) addToBackStack(null)
        }.commit()
    } catch (e: IllegalStateException) {
        // do nothing
    }
}