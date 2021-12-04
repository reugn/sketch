package io.github.reugn.sketch.filter

interface PixelFilter : ImageFilter {

    fun apply(rgb: Int): Int
}
