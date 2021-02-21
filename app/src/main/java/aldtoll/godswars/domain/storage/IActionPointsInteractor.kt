package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.ActionPoint
import io.reactivex.Observable

interface IActionPointsInteractor {

    fun update(actionPoints: MutableList<ActionPoint>)

    fun get(): Observable<MutableList<ActionPoint>>

    fun value(): MutableList<ActionPoint>
}