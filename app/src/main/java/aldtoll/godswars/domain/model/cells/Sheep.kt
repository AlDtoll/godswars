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

    fun show(position: Int) {
        visit(position)
        cells[position].show()
        changeShowStatus(position, true)
    }

    fun hide(position: Int) {
        cells[position].hide()
        changeShowStatus(position, false)
    }

    private fun showNeighbors(position: Int) {
        val rowNumber = position / (COLUMN_NUMBER)
        val columnNumber = position % (COLUMN_NUMBER)
        if (rowNumber != 0) {
            val upCell = cells[position - COLUMN_NUMBER]
            if (canBeShowed(upCell.bottomWall.type)) {
                show(position - COLUMN_NUMBER)
            }
        }
        if (rowNumber != DatabaseInteractor.ROW_NUMBER - 1) {
            val bottomCell = cells[position + COLUMN_NUMBER]
            if (canBeShowed(bottomCell.upWall.type)) {
                show(position + COLUMN_NUMBER)
            }
        }
        if (columnNumber != 0) {
            val leftCell = cells[position - 1]
            if (canBeShowed(leftCell.rightWall.type)) {
                show(position - 1)
            }
        }
        if (columnNumber != COLUMN_NUMBER - 1) {
            val rightCell = cells[position + 1]
            if (canBeShowed(rightCell.leftWall.type)) {
                show(position + 1)
            }
        }
    }

    private fun changeShowStatus(position: Int, show: Boolean) {
        val rowNumber = position / (COLUMN_NUMBER)
        val columnNumber = position % (COLUMN_NUMBER)
        if (rowNumber != 0) {
            cells[position - COLUMN_NUMBER].bottomWall.show = show
        }
        if (rowNumber != DatabaseInteractor.ROW_NUMBER - 1) {
            cells[position + COLUMN_NUMBER].upWall.show = show
        }
        if (columnNumber != 0) {
            cells[position - 1].rightWall.show = show
        }
        if (columnNumber != COLUMN_NUMBER - 1) {
            cells[position + 1].leftWall.show = show
        }
    }

    private fun canBeShowed(type: Wall.Type): Boolean {
        return when (type) {
            Wall.Type.WALL -> false
            Wall.Type.DOOR -> false
            Wall.Type.EMPTY -> true
            Wall.Type.NO -> true
        }
    }

    fun showGuests() {
        val filter = cells.filter { !it.room.persons.isNullOrEmpty() }
        filter.forEach {
            showNeighbors(it.position.toInt())
        }
    }
}