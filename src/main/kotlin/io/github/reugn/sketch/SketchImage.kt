package io.github.reugn.sketch

import io.github.reugn.sketch.filter.FilterProcessor
import io.github.reugn.sketch.filter.ImageFilter
import io.github.reugn.sketch.geom.Pixel
import io.github.reugn.sketch.geom.Position
import io.github.reugn.sketch.geom.Rectangle
import io.github.reugn.sketch.transformer.BlurTransformer
import io.github.reugn.sketch.transformer.PixelateTransformer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.newFixedThreadPoolContext
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.RoundRectangle2D
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

class SketchImage(private val bufferedImage: BufferedImage) : Sketch {

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    companion object {
        private val sketchThreadPoolContext = newFixedThreadPoolContext(
            Runtime.getRuntime().availableProcessors(),
            "sketch-context"
        )

        private val sketchScope: CoroutineScope =
            CoroutineScope(SupervisorJob() + sketchThreadPoolContext)
    }

    override fun asBufferedImage(): BufferedImage {
        return bufferedImage
    }

    override suspend fun resize(scaleX: Float, scaleY: Float): Sketch = sketchScope.run {
        require(scaleX in 0.0..1.0) {
            throw IllegalArgumentException("scaleX should be a value between 0..1")
        }
        require(scaleY in 0.0..1.0) {
            throw IllegalArgumentException("scaleY should be a value between 0..1")
        }

        val newWidth = (bufferedImage.width * scaleX).toInt()
        val newHeight = (bufferedImage.height * scaleY).toInt()

        val resultingImage = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT)
        val dstImage = BufferedImage(newWidth, newHeight, bufferedImage.type)
        val graphics: Graphics = dstImage.graphics
        graphics.drawImage(resultingImage, 0, 0, null)
        graphics.dispose()

        return SketchImage(dstImage)
    }

    override suspend fun crop(rectangle: Rectangle): Sketch = sketchScope.run {
        val dstImage = BufferedImage(rectangle.width, rectangle.height, bufferedImage.type)
        val graphics: Graphics = dstImage.graphics
        graphics.drawImage(
            bufferedImage,
            0,
            0,
            rectangle.width,
            rectangle.height,
            rectangle.x,
            rectangle.y,
            rectangle.x + rectangle.width,
            rectangle.y + rectangle.height,
            null
        )
        graphics.dispose()

        return SketchImage(dstImage)
    }

    override suspend fun crop(position: Position, radius: Int): Sketch = sketchScope.run {
        require(radius > 0)
        val dstImage = BufferedImage(radius, radius, TYPE_INT_ARGB)
        val g2d: Graphics2D = dstImage.createGraphics()
        g2d.color = Color.WHITE
        g2d.clip = Ellipse2D.Float(0f, 0f, radius.toFloat(), radius.toFloat())
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        val srcImage = crop(Rectangle(position.x, position.y, radius, radius)).asBufferedImage()
        g2d.drawImage(srcImage, 0, 0, null)
        g2d.dispose()

        return SketchImage(dstImage)
    }

    override suspend fun rotate(angle: Int): Sketch = sketchScope.run {
        require(angle in 0..360) {
            throw IllegalArgumentException("angle should be a value between 0..360")
        }

        val rads = Math.toRadians(angle.toDouble())
        val sin = abs(sin(rads))
        val cos = abs(cos(rads))

        val newWidth = floor(bufferedImage.width * cos + bufferedImage.height * sin).toInt()
        val newHeight = floor(bufferedImage.height * cos + bufferedImage.width * sin).toInt()

        val transform = AffineTransform()
        transform.translate((newWidth / 2).toDouble(), (newHeight / 2).toDouble())
        transform.rotate(rads, 0.0, 0.0)
        transform.translate((-bufferedImage.width / 2).toDouble(), (-bufferedImage.height / 2).toDouble())

        val rotateOp = AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR)
        val rotatedImage = rotateOp.filter(bufferedImage, null)

        return SketchImage(rotatedImage)
    }

    override suspend fun border(width: Int, color: Color): Sketch = sketchScope.run {
        require(width > 0)
        val borderedWidth = bufferedImage.width + (width * 2)
        val borderedHeight = bufferedImage.height + (width * 2)

        val borderedImage = BufferedImage(borderedWidth, borderedHeight, bufferedImage.type)
        val g2d = borderedImage.createGraphics()
        g2d.color = color
        g2d.fillRect(0, 0, borderedWidth, borderedHeight)
        g2d.drawImage(bufferedImage, width, width, null)
        g2d.dispose()

        return SketchImage(borderedImage)
    }

    override suspend fun borderRadius(radius: Int): Sketch = sketchScope.run {
        require(radius > 0)
        val width = bufferedImage.width
        val height = bufferedImage.height
        val dstImage = BufferedImage(width, height, TYPE_INT_ARGB)

        val g2d = dstImage.createGraphics()
        g2d.composite = AlphaComposite.Src
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.color = Color.WHITE
        g2d.fill(
            RoundRectangle2D.Float(
                0f, 0f,
                width.toFloat(),
                height.toFloat(),
                radius.toFloat(),
                radius.toFloat()
            )
        )

        g2d.composite = AlphaComposite.SrcIn
        g2d.drawImage(bufferedImage, 0, 0, null)
        g2d.dispose()

        return SketchImage(dstImage)
    }

    override suspend fun inline(
        position: Position, font: Font,
        color: Color, text: String
    ): Sketch = sketchScope.run {
        val g2d: Graphics2D = bufferedImage.createGraphics()
        g2d.font = font
        g2d.color = color
        g2d.drawString(text, position.x, position.y)
        g2d.dispose()

        return SketchImage(bufferedImage)
    }

    override suspend fun inline(position: Position, image: Sketch): Sketch = sketchScope.run {
        val g2d: Graphics2D = bufferedImage.createGraphics()
        g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER)
        g2d.drawImage(image.asBufferedImage(), position.x, position.y, null)
        g2d.dispose()

        return SketchImage(bufferedImage)
    }

    override suspend fun setPixel(pixel: Pixel): Sketch = sketchScope.run {
        bufferedImage.setRGB(
            pixel.position.x,
            pixel.position.y,
            pixel.color.rgb
        )
        return SketchImage(bufferedImage)
    }

    override suspend fun setPixels(pixels: Collection<Pixel>): Sketch = sketchScope.run {
        pixels.forEach {
            bufferedImage.setRGB(
                it.position.x,
                it.position.y,
                it.color.rgb
            )
        }
        return SketchImage(bufferedImage)
    }

    override suspend fun filter(vararg filter: ImageFilter): Sketch = sketchScope.run {
        return SketchImage(FilterProcessor(bufferedImage, filter.asList()).filter())
    }

    override suspend fun blur(dimensions: Rectangle, radius: Int): Sketch = sketchScope.run {
        require(radius > 0)
        val subImage = bufferedImage.getSubimage(dimensions.x, dimensions.y, dimensions.width, dimensions.height)
        val blurredSubImage = BlurTransformer(radius).transform(subImage)
        return inline(dimensions.toPosition(), SketchImage(blurredSubImage))
    }

    override suspend fun blur(radius: Int): Sketch = sketchScope.run {
        require(radius > 0)
        return SketchImage(BlurTransformer(radius).transform(bufferedImage))
    }

    override suspend fun pixelate(dimensions: Rectangle, pixelSize: Int): Sketch = sketchScope.run {
        require(pixelSize > 0)
        val subImage = crop(dimensions).asBufferedImage()
        val pixelatedSubImage = PixelateTransformer(pixelSize).transform(subImage)
        return inline(dimensions.toPosition(), SketchImage(pixelatedSubImage))
    }

    override suspend fun pixelate(pixelSize: Int): Sketch = sketchScope.run {
        require(pixelSize > 0)
        return SketchImage(PixelateTransformer(pixelSize).transform(bufferedImage))
    }
}
