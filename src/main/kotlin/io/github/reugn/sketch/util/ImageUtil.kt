package io.github.reugn.sketch.util

import java.awt.image.BufferedImage

object ImageUtil {

    /**
     * Returns a copy of the given BufferedImage.
     */
    fun deepCopy(bi: BufferedImage): BufferedImage {
        val colorModel = bi.colorModel
        val isAlphaPremultiplied = colorModel.isAlphaPremultiplied
        val raster = bi.copyData(null)
        return BufferedImage(colorModel, raster, isAlphaPremultiplied, null)
    }
}
