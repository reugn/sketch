package io.github.reugn.sketch

import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SketchRotateTest : SketchTestBase() {

    @Test
    fun `Test rotate image`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.rotate(45),
                destImagePath("hummingbird_rotate.png")
            )
        }
    }
}
