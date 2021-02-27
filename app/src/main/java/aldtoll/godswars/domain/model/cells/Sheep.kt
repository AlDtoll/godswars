package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.DatabaseInteractor.Companion.COLUMN_NUMBER

class Sheep(
) {

    var cells: ArrayList<Cell> = arrayListOf()

    fun visit(position: Int) {
        cells[position].visit()
        val rowNumber = position / (COLUMN_NUMBER)
        val columnNumber = position % (COLUMN_NUMBER)
        if (rowNumber != 0) {
            cells[position - COLUMN_NUMBER].bottomWall.visited = true
        }
        if (rowNumber != DatabaseInteractor.ROW_NUMBER - 1) {
            cells[position + COLUMN_NUMBER].upWall.visited = true
        }
        if (columnNumber != 0) {
            cells[position - 1].rightWall.visited = true
        }
        if (columnNumber != COLUMN_NUMBER - 1) {
            cells[position + 1].leftWall.visited = true
        }
    }
}