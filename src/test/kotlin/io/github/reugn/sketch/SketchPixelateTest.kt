package io.github.reugn.sketch

import io.github.reugn.sketch.geom.Rectangle
import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SketchPixelateTest : SketchTestBase() {

    @Test
    fun `Test pixelate entire image`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.pixelate(10),
                destImagePath("hummingbird_pixelate_full.jpg")
            )
        }
    }

    @Test
    fun `Test pixelate image part`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.pixelate(Rectangle(0, 0, 100, 50), 10),
                destImagePath("hummingbird_pixelate_partial.jpg")
            )
        }
    }
}
