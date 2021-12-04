package io.github.reugn.sketch.filter

import java.awt.Color
import java.lang.Integer.min

class ColorMaskFilter(private val color: Color) : PixelFilter {

    override fun apply(rgb: Int): Int {
        val r = min((rgb shr 16 and 0xFF) + color.red, 255)
        val g = min((rgb shr 8 and 0xFF) + color.green, 255)
        val b = min((rgb and 0xFF) + color.blue, 255)

        return Color(r, g, b).rgb
    }
}
