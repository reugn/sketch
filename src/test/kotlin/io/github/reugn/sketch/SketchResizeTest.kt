package io.github.reugn.sketch

import io.github.reugn.sketch.util.SketchIO
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class SketchResizeTest : SketchTestBase() {

    @Test
    fun `Test image resize`(): Unit = runBlocking {
        val orig = SketchIO.load(sourceImagePath)
        assertTrue {
            SketchIO.save(
                orig.resize(0.5f, 0.5f),
                destImagePath("hummingbird_resized.jpg")
            )
        }
    }
}
