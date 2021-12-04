package io.github.reugn.sketch

import io.github.reugn.sketch.filter.ImageFilter
import io.github.reugn.sketch.geom.Pixel
import io.github.reugn.sketch.geom.Position
import io.github.reugn.sketch.geom.Rectangle
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage

interface Sketch {

    /**
     * Returns the underlying BufferedImage.
     */
    fun asBufferedImage(): BufferedImage

    /**
     * Scales the image using the specified ratio.
     * The parameters should be a value between 0..1.
     * Returns a new Sketch instance.
     */
    suspend fun resize(
        scaleX: Float,
        scaleY: Float
    ): Sketch

    /**
     * Crops the image within the area specified by the Rectangle parameter.
     * Returns a new Sketch instance.
     */
    suspend fun crop(rectangle: Rectangle): Sketch

    /**
     * Crops the image as a circle, using the position parameter as a new
     * image center, and the radius.
     * Returns a new Sketch instance.
     */
    suspend fun crop(
        position: Position,
        radius: Int
    ): Sketch

    /**
     * Rotates the image.
     * The angle should be a value between 0..360.
     * Returns a new Sketch instance.
     */
    suspend fun rotate(angle: Int): Sketch

    /**
     * Adds a border to the image specified by the width and color arguments.
     * Returns a new Sketch instance.
     */
    suspend fun border(
        width: Int,
        color: Color
    ): Sketch

    /**
     * Adds border radius effect using the specified radius parameter.
     * Returns a new Sketch instance.
     */
    suspend fun borderRadius(radius: Int): Sketch

    /**
     * Inlines a text into the image at the specified position.
     * Returns a new Sketch instance.
     */
    suspend fun inline(
        position: Position,
        font: Font,
        color: Color,
        text: String
    ): Sketch

    /**
     * Inlines another image into the image at the specified position.
     * Returns a new Sketch instance.
     */
    suspend fun inline(
        position: Position,
        image: Sketch
    ): Sketch

    /**
     * Sets a single pixel.
     * Returns a new Sketch instance.
     */
    suspend fun setPixel(pixel: Pixel): Sketch

    /**
     * Sets a collection of pixels.
     * Returns a new Sketch instance.
     */
    suspend fun setPixels(pixels: Collection<Pixel>): Sketch

    /**
     * Applies various filters to the image.
     * Implemented filters:
     * - [io.github.reugn.sketch.filter.BrightnessFilter]
     * - [io.github.reugn.sketch.filter.ContrastFilter]
     * - [io.github.reugn.sketch.filter.ColorMaskFilter]
     * - [io.github.reugn.sketch.filter.GrayscaleFilter]
     * - [io.github.reugn.sketch.filter.InvertColorFilter]
     * - [io.github.reugn.sketch.filter.OpacityFilter]
     *
     * The filters are applied in the order in which they are specified.
     * Returns a new Sketch instance.
     */
    suspend fun filter(vararg filter: ImageFilter): Sketch

    /**
     * Blurs a part of the image specified by the Rectangle parameter.
     * Returns a new Sketch instance.
     */
    suspend fun blur(
        dimensions: Rectangle,
        radius: Int = 5
    ): Sketch

    /**
     * Blurs the entire image.
     * Returns a new Sketch instance.
     */
    suspend fun blur(radius: Int = 5): Sketch

    /**
     * Pixelates a part of the image specified by the Rectangle parameter.
     * Returns a new Sketch instance.
     */
    suspend fun pixelate(
        dimensions: Rectangle,
        pixelSize: Int = 5
    ): Sketch

    /**
     * Pixelates the entire image.
     * Returns a new Sketch instance.
     */
    suspend fun pixelate(pixelSize: Int = 5): Sketch
}
