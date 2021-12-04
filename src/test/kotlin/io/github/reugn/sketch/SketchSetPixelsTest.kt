package io.github.reugn.sketch

import io.github.reugn.sketch.geom.Pixel
import io.github.reugn.sketch.geom.Position
import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.awt.Color
import kotlin.test.assertTrue

class SketchSetPixelsTest : SketchTestBase() {

    @Test
    fun `Test set pixels`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        val width = orig.asBufferedImage().width
        val pixels = (0 until width).map { Pixel(Position(it, 10), Color.BLACK) }.toList()
        assertTrue {
            SketchIO.save(
                orig.setPixels(pixels),
                destImagePath("hummingbird_set_pixels.jpg")
            )
        }
    }
}
