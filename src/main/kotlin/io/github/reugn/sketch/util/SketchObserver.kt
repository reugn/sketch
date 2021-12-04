package io.github.reugn.sketch.util

import io.github.reugn.sketch.Sketch
import io.github.reugn.sketch.SketchImage
import kotlinx.coroutines.CompletableDeferred
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver

class SketchObserver(private val bufferedImage: BufferedImage) : ImageObserver {

    private val deferred = CompletableDeferred<SketchImage>()

    override fun imageUpdate(
        img: Image?, infoflags: Int,
        x: Int, y: Int,
        width: Int, height: Int
    ): Boolean {
        if (img != null) {
            if (infoflags and ImageObserver.ALLBITS != 0) {
                deferred.complete(SketchImage(bufferedImage))
            } else if (infoflags and (ImageObserver.ERROR or ImageObserver.ABORT) != 0) {
                deferred.completeExceptionally(Exception("error processing image"))
            }
        } else {
            deferred.completeExceptionally(Exception("img is null"))
        }
        return infoflags and (ImageObserver.ALLBITS or ImageObserver.ABORT) == 0
    }

    suspend fun await(): Sketch {
        return deferred.await()
    }
}
