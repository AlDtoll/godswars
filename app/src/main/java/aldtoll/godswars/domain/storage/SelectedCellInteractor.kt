package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.cells.Cell
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SelectedCellInteractor : ISelectedCellInteractor {

    private val item = BehaviorSubject.create<Cell>()

    override fun update(cell: Cell) {
        item.onNext(cell)
    }

    override fun get(): Observable<Cell> = item

    override fun value(): Cell = item.value ?: Cell.nothing()
}