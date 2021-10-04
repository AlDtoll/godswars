package aldtoll.godswars.domain.executor

import aldtoll.godswars.domain.model.cells.Cell

interface IClickCellIntreactor {

    fun execute(cell: Cell)
}