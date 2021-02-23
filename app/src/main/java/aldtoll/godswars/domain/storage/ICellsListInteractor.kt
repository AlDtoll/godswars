package aldtoll.godswars.domain.storage

import aldtoll.godswars.domain.model.cells.Cell
import io.reactivex.Observable

interface ICellsListInteractor {

    fun update(cells: MutableList<Cell>)

    fun get(): Observable<MutableList<Cell>>

    fun value(): MutableList<Cell>

}