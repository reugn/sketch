package io.github.reugn.sketch.transformer

import java.awt.image.BufferedImage
import java.awt.image.Raster

class PixelateTransformer(private val pixelSize: Int) : ImageTransformer {

    override fun transform(image: BufferedImage): BufferedImage {
        val src: Raster = image.data
        val dest = src.createCompatibleWritableRaster()

        var y = 0
        while (y < src.height) {
            var x = 0
            while (x < src.width) {
                var pixel = DoubleArray(3)
                pixel = src.getPixel(x, y, pixel)

                var yd = y
                while (yd < y + pixelSize && yd < dest.height) {
                    var xd = x
                    while (xd < x + pixelSize && xd < dest.width) {
                        dest.setPixel(xd, yd, pixel)
                        xd++
                    }
                    yd++
                }
                x += pixelSize
            }
            y += pixelSize
        }

        return BufferedImage(
            image.colorModel, dest, image.colorModel.isAlphaPremultiplied, null
        )
    }
}
