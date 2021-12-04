package io.github.reugn.sketch.geom

data class Rectangle(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
) {
    fun toPosition(): Position {
        return Position(x, y)
    }

    fun toDimension(): Dimension {
        return Dimension(width, height)
    }
}
