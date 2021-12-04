package io.github.reugn.sketch

import io.github.reugn.sketch.geom.Rectangle
import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SketchBlurTest : SketchTestBase() {

    @Test
    fun `Test blur entire image`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.blur(10),
                destImagePath("hummingbird_blur_full.jpg")
            )
        }
    }

    @Test
    fun `Test blur image part`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.blur(Rectangle(10, 10, 50, 50), 10),
                destImagePath("hummingbird_blur_partial.jpg")
            )
        }
    }
}
