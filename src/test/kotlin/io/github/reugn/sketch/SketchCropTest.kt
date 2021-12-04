package io.github.reugn.sketch

import io.github.reugn.sketch.geom.Position
import io.github.reugn.sketch.geom.Rectangle
import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SketchCropTest : SketchTestBase() {

    @Test
    fun `Test crop image`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        val rectangle = Rectangle(
            40, 30, orig.asBufferedImage().width / 2, orig.asBufferedImage().height / 2
        )
        assertTrue {
            SketchIO.save(
                orig.crop(rectangle),
                destImagePath("hummingbird_crop_rect.jpg")
            )
        }
    }

    @Test
    fun `Test crop image as a circle`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.crop(Position(65, 25), 50),
                destImagePath("hummingbird_crop_circle.png")
            )
        }
    }
}
