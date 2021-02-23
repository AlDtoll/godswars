package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.cells.Cell
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class CellsListInteractor : ICellsListInteractor {

    private val list = BehaviorSubject.create<MutableList<Cell>>()

    override fun update(cells: MutableList<Cell>) {
        list.onNext(cells)
    }

    override fun get(): Observable<MutableList<Cell>> = list

    override fun value(): MutableList<Cell> = list.value ?: emptyList<Cell>().toMutableList()

}