package io.github.reugn.sketch

import io.github.reugn.sketch.geom.Position
import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.awt.Color
import java.awt.Font
import kotlin.test.assertTrue

class SketchInlineTest : SketchTestBase() {

    @Test
    fun `Test inline image`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.inline(Position(10, 10), SketchImage(rectangleImage())),
                destImagePath("hummingbird_inline_image.jpg")
            )
        }
    }

    @Test
    fun `Test inline text`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.inline(
                    Position(30, 36),
                    Font("Arial", Font.BOLD, 32),
                    Color.BLACK,
                    "Sketch"
                ),
                destImagePath("hummingbird_inline_text.jpg")
            )
        }
    }
}
