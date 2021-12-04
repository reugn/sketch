package io.github.reugn.sketch.util

import io.github.reugn.sketch.Sketch
import io.github.reugn.sketch.SketchImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

/**
 * An object containing IO utility methods to load and save Sketch images.
 */
@Suppress("BlockingMethodInNonBlockingContext")
object SketchIO {

    private val sketchIOContext = Dispatchers.IO

    @Throws(IOException::class)
    suspend fun load(filePath: String): Sketch = withContext(sketchIOContext) {
        SketchImage(ImageIO.read(File(filePath)))
    }

    @Throws(IOException::class)
    suspend fun load(stream: InputStream): Sketch = withContext(sketchIOContext) {
        SketchImage(ImageIO.read(stream))
    }

    @Throws(IOException::class)
    suspend fun load(byteArray: ByteArray): Sketch = withContext(sketchIOContext) {
        SketchImage(ImageIO.read(ByteArrayInputStream(byteArray)))
    }

    @Throws(IOException::class)
    suspend fun save(
        image: Sketch,
        filePath: String
    ): Boolean {
        val outputFile = File(filePath)
        val format = fetchImageFormat(filePath)

        return withContext(sketchIOContext) {
            ImageIO.write(image.asBufferedImage(), format.name, outputFile)
        }
    }

    @Throws(IOException::class)
    suspend fun saveCompressed(
        image: Sketch,
        filePath: String,
        quality: Float = 1f
    ) {
        val outputFile = File(filePath)
        val format = fetchImageFormat(filePath)

        val imageWriter = ImageIO.getImageWritersByFormatName(format.name).next()
        val imageWriteParam = imageWriter.defaultWriteParam
        imageWriteParam.compressionMode = ImageWriteParam.MODE_EXPLICIT
        imageWriteParam.compressionQuality = quality

        withContext(sketchIOContext) {
            imageWriter.output = ImageIO.createImageOutputStream(outputFile)
            val outputImage = IIOImage(image.asBufferedImage(), null, null)
            imageWriter.write(null, outputImage, imageWriteParam)
            imageWriter.dispose()
        }
    }

    private fun fetchImageFormat(filePath: String): ImageFormat {
        val extIndex = filePath.lastIndexOf('.')
        if (extIndex == -1) {
            throw IOException("Unknown file format; specify file extension.")
        }
        val fileExtension = filePath.substring(extIndex + 1)
        return ImageFormat.valueOf(fileExtension.uppercase())
    }
}
