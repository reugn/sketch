package io.github.reugn.sketch

import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.awt.Color
import kotlin.test.assertTrue

class SketchBorderTest : SketchTestBase() {

    @Test
    fun `Test image border`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.border(3, Color.red),
                destImagePath("hummingbird_border.png")
            )
        }
    }

    @Test
    fun `Test image border with radius`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.borderRadius(30).border(3, Color.red).borderRadius(30),
                destImagePath("hummingbird_border_with_radius.png")
            )
        }
    }

    @Test
    fun `Test image radius`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.borderRadius(30),
                destImagePath("hummingbird_border_radius.png")
            )
        }
    }
}
