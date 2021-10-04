package aldtoll.godswars.domain.model.cells

import aldtoll.godswars.domain.DatabaseInteractor
import aldtoll.godswars.domain.DatabaseInteractor.Companion.COLUMN_NUMBER

class StarShip {

    var cells: ArrayList<Cell> = arrayListOf()

    fun visit(position: Int) {
        cells[position].visit()
        val rowNumber = position / (COLUMN_NUMBER)
        val columnNumber = position % (COLUMN_NUMBER)
        if (rowNumber != 0) {
            cells[position - COLUMN_NUMBER].visited = true
        }
        if (rowNumber != DatabaseInteractor.ROW_NUMBER - 1) {
            cells[position + COLUMN_NUMBER].visited = true
        }
        if (columnNumber != 0) {
            cells[position - 1].visited = true
        }
        if (columnNumber != COLUMN_NUMBER - 1) {
            cells[position + 1].visited = true
        }
    }

    fun show(position: Int) {
        visit(position)
        cells[position].show()
        changeShowStatusWithNearestCell(position, true)
    }

    fun hide(position: Int) {
        cells[position].hide()
        changeShowStatusWithNearestCell(position, false)
    }

    private fun showNeighbors(position: Int) {
        changeShowStatusWithNearestCell(position, true)
        changeShowStatusWithDistantCell(position, true)
    }

    fun hideNeighbors(position: Int) {
        changeShowStatusWithNearestCell(position, false)
    }

    private fun changeShowStatusWithNearestCell(position: Int, show: Boolean) {
        val rowNumber = position / (COLUMN_NUMBER)
        val columnNumber = position % (COLUMN_NUMBER)
        if (rowNumber != 0) {
            cells[position - COLUMN_NUMBER].show = show
            if (show) {
                cells[position - COLUMN_NUMBER].visited = true
            }
        }
        if (rowNumber != DatabaseInteractor.ROW_NUMBER - 1) {
            cells[position + COLUMN_NUMBER].show = show
            if (show) {
                cells[position + COLUMN_NUMBER].visited = true
            }
        }
        if (columnNumber != 0) {
            cells[position - 1].show = show
            if (show) {
                cells[position - 1].visited = true
            }
        }
        if (columnNumber != COLUMN_NUMBER - 1) {
            cells[position + 1].show = show
            if (show) {
                cells[position + 1].visited = true
            }
        }
    }

    private fun changeShowStatusWithDistantCell(position: Int, show: Boolean) {
        val rowNumber = position / (COLUMN_NUMBER)
        val columnNumber = position % (COLUMN_NUMBER)
        if (rowNumber > 1) {
            if (canBeShowedNextCell(cells[position - COLUMN_NUMBER].type)) {
                cells[position - COLUMN_NUMBER - COLUMN_NUMBER].show = show
                if (show) {
                    cells[position - COLUMN_NUMBER - COLUMN_NUMBER].visited = true
                }
            }
        }
        if (rowNumber < DatabaseInteractor.ROW_NUMBER - 2) {
            if (canBeShowedNextCell(cells[position + COLUMN_NUMBER].type)) {
                cells[position + COLUMN_NUMBER + COLUMN_NUMBER].show = show
                if (show) {
                    cells[position + COLUMN_NUMBER + COLUMN_NUMBER].visited = true
                }
            }
        }
        if (columnNumber > 1) {
            if (canBeShowedNextCell(cells[position - 1].type)) {
                cells[position - 2].show = show
                if (show) {
                    cells[position - 2].visited = true
                }
            }
        }
        if (columnNumber < COLUMN_NUMBER - 2) {
            if (canBeShowedNextCell(cells[position + 1].type)) {
                cells[position + 2].show = show
                if (show) {
                    cells[position + 2].visited = true
                }
            }
        }
    }

    private fun canBeShowedNextCell(type: Cell.Type): Boolean {
        return when (type) {
            Cell.Type.WALL -> false
            Cell.Type.DOOR -> false
            else -> true
        }
    }

    fun showGuests() {
        val filter = cells.filter { !it.persons.isNullOrEmpty() }
        filter.forEach {
            it.show()
            showNeighbors(it.position.toInt())
        }
    }

    fun enabledForSelectedPerson(position: Int) {

    }

    fun showEnabledForGuest(position: Int) {
        val rowNumber = position / (COLUMN_NUMBER)
        val columnNumber = position % (COLUMN_NUMBER)
        if (rowNumber != 0) {
            val upCell = cells[position - COLUMN_NUMBER]
            if (canBeCellEnabled(upCell.type)) {
                upCell.enabled = true
            }
        }
        if (rowNumber != DatabaseInteractor.ROW_NUMBER - 1) {
            val bottomCell = cells[position + COLUMN_NUMBER]
            if (canBeCellEnabled(bottomCell.type)) {
                bottomCell.enabled = true
            }
        }
        if (columnNumber != 0) {
            val leftCell = cells[position - 1]
            if (canBeCellEnabled(leftCell.type)) {
                leftCell.enabled = true
            }
        }
        if (columnNumber != COLUMN_NUMBER - 1) {
            val rightCell = cells[position + 1]
            if (canBeCellEnabled(rightCell.type)) {
                rightCell.enabled = true
            }
        }
    }

    private fun canBeCellEnabled(type: Cell.Type): Boolean {
        return when (type) {
            Cell.Type.WALL -> false
            Cell.Type.EMPTY -> false
            else -> true
        }
    }

    fun selectCell(position: Int) {
        if (cells[position].selected) {
            cells[position].selected = false
        } else {
            cells.forEach {
                it.selected = false
            }
            cells[position].selected = true
        }
    }

    fun hideAll() {
        cells.forEach {
            it.hide()
        }
    }

    fun disabledAll() {
        cells.forEach {
            it.disable()
        }
    }
}