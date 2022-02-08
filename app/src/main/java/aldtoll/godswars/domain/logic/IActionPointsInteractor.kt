package aldtoll.godswars.domain.logic

import aldtoll.godswars.domain.model.ActionPoint
import io.reactivex.Observable

interface IActionPointsInteractor {

    fun get(): Observable<MutableList<ActionPoint>>
}