package io.github.reugn.sketch.filter

import kotlin.math.pow

class GrayscaleFilter : PixelFilter {

    override fun apply(rgb: Int): Int {
        val r = rgb shr 16 and 0xFF
        val g = rgb shr 8 and 0xFF
        val b = rgb and 0xFF

        // Normalize and gamma correct:
        val rr = (r / 255.0).pow(2.2).toFloat()
        val gg = (g / 255.0).pow(2.2).toFloat()
        val bb = (b / 255.0).pow(2.2).toFloat()

        // Calculate luminance:
        val lum = (0.2126 * rr + 0.7152 * gg + 0.0722 * bb).toFloat()

        val grayLevel = (255.0 * lum.toDouble().pow(1.0 / 2.2)).toInt()
        return (grayLevel shl 16) + (grayLevel shl 8) + grayLevel
    }
}
