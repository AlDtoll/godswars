package aldtoll.godswars.domain.model.cells

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Cell(
    val room: Room = Room(),
    val upWall: Wall = Wall(true),
    val rightWall: Wall = Wall(),
    val bottomWall: Wall = Wall(true),
    val leftWall: Wall = Wall(),
    val cross: Wall = Wall()
) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Cell {
            val roomMap = map["room"]
            var room = Room()
            if (roomMap != null) {
                room = Room.fromMap(roomMap as HashMap<String, Any>)
            }
            val upWallMap = map["upWall"]
            var upWall = Wall()
            if (upWallMap != null) {
                upWall = Wall.fromMap(upWallMap as HashMap<String, Any>)
            }
            val rightWallMap = map["rightWall"]
            var rightWall = Wall()
            if (rightWallMap != null) {
                rightWall = Wall.fromMap(rightWallMap as HashMap<String, Any>)
            }
            val bottomWallMap = map["bottomWall"]
            var bottomWall = Wall(true)
            if (roomMap != null) {
                bottomWall = Wall.fromMap(bottomWallMap as HashMap<String, Any>)
            }
            val leftWallMap = map["leftWall"]
            var leftWall = Wall()
            if (leftWallMap != null) {
                leftWall = Wall.fromMap(rightWallMap as HashMap<String, Any>)
            }
            val crossMap = map["cross"]
            var cross = Wall()
            if (crossMap != null) {
                cross = Wall.fromMap(crossMap as HashMap<String, Any>)
            }
            return Cell(
                room,
                upWall,
                rightWall,
                bottomWall,
                leftWall,
                cross
            )
        }
    }


    fun visit() {
        room.visited = true
        upWall.visited = true
        rightWall.visited = true
        bottomWall.visited = true
        leftWall.visited = true
    }

    fun show() {
        room.show = true
        upWall.show = true
        rightWall.show = true
        bottomWall.show = true
        leftWall.show = true
    }

    fun hide() {
        room.show = false
        upWall.show = false
        rightWall.show = false
        bottomWall.show = false
        leftWall.show = false
    }
}