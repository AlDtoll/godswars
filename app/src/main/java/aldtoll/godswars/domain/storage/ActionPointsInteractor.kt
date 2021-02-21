package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.ActionPoint
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ActionPointsInteractor : IActionPointsInteractor {

    private val list = BehaviorSubject.create<MutableList<ActionPoint>>()

    override fun update(actionPoints: MutableList<ActionPoint>) {
        list.onNext(actionPoints)
    }

    override fun get(): Observable<MutableList<ActionPoint>> = list

    override fun value(): MutableList<ActionPoint> =
        list.value ?: emptyList<ActionPoint>().toMutableList()
}