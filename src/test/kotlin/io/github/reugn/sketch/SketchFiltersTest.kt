package io.github.reugn.sketch

import io.github.reugn.sketch.filter.*
import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.awt.Color
import kotlin.test.assertTrue

class SketchFiltersTest : SketchTestBase() {

    @Test
    fun `Test brightness filter`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.filter(BrightnessFilter(0.7f)),
                destImagePath("hummingbird_brightness_filter.jpg")
            )
        }
    }

    @Test
    fun `Test contrast filter`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.filter(ContrastFilter(0.7f)),
                destImagePath("hummingbird_contrast_filter.jpg")
            )
        }
    }

    @Test
    fun `Test grayscale filter`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.filter(GrayscaleFilter()),
                destImagePath("hummingbird_grayscale_filter.jpg")
            )
        }
    }

    @Test
    fun `Test invert colors filter`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.filter(InvertColorFilter()),
                destImagePath("hummingbird_invert_filter.jpg")
            )
        }
    }

    @Test
    fun `Test color mask filter`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.filter(ColorMaskFilter(Color.BLUE)),
                destImagePath("hummingbird_color_mask_filter.jpg")
            )
        }
    }

    @Test
    fun `Test opacity filter`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.filter(OpacityFilter(0.8f)),
                destImagePath("hummingbird_opacity_filter.jpg")
            )
        }
    }
}
