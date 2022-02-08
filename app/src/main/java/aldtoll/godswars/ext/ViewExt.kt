package aldtoll.godswars.ext

import android.view.View

fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}