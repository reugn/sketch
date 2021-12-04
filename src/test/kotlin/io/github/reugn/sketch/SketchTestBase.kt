package io.github.reugn.sketch

import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage

open class SketchTestBase {

    companion object {
        protected const val sourceImageName = "hummingbird_original.jpg"
    }

    protected val sourceImagePath by lazy { sourceImagePath() }

    private fun sourceImagePath(): String {
        val url = this::class.java.classLoader.getResource(sourceImageName)
        return url!!.path
    }

    protected fun destImagePath(fileName: String): String {
        return "test-images/$fileName"
    }

    protected fun rectangleImage(): BufferedImage {
        val img = BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB)
        val g2d: Graphics2D = img.createGraphics()
        g2d.color = Color.GREEN
        g2d.fill(Rectangle(0, 0, 25, 25))
        g2d.dispose()
        return img
    }
}
