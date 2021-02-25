package aldtoll.godswars.domain.model.cells

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Cell(
    val room: Room = Room(),
    val rightWall: Wall = Wall(),
    val bottomWall: Wall = Wall(true),
    val cross: Wall = Wall()
) {

    companion object {
        fun fromMap(map: HashMap<String, Any>): Cell {
            val roomMap = map["room"]
            var room = Room()
            if (roomMap != null) {
                room = Room.fromMap(roomMap as HashMap<String, Any>)
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
            val crossMap = map["cross"]
            var cross = Wall()
            if (crossMap != null) {
                cross = Wall.fromMap(crossMap as HashMap<String, Any>)
            }
            return Cell(
                room,
                rightWall,
                bottomWall,
                cross
            )
        }
    }

    enum class Type {
        ROOM,
        WALL,
        DOOR,
        EMPTY,
        PIER,
        ENGINE,
        REACTOR,
        BRIDGE,
        TERMINAL
    }
}