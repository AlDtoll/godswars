package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.cells.Cell
import io.reactivex.Observable

interface ICellInteractor {

    fun update(cell: Cell)

    fun get(): Observable<Cell>

    fun value(): Cell
}