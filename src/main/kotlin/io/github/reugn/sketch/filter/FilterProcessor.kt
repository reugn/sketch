package io.github.reugn.sketch.filter

import java.awt.image.BufferedImage

class FilterProcessor(
    private var image: BufferedImage,
    private val filters: Collection<ImageFilter>
) {

    private var rescaleOpBuilder: RescaleOpBuilder? = null

    fun filter(): BufferedImage {
        if (filters.isEmpty()) {
            return image
        }

        val destination = BufferedImage(image.width, image.height, image.type)
        val g2d = destination.createGraphics()

        val (pixelFilters, otherFilters) = filters.partition { it is PixelFilter }

        otherFilters.forEach {
            when (it) {
                is RescaleOpFilter -> it.apply(rescaleOpBuilder())
                is Graphics2DFilter -> it.apply(g2d)
                else -> throw IllegalArgumentException("Invalid Filter type")
            }
        }

        @Suppress("UNCHECKED_CAST")
        if (pixelFilters.isNotEmpty()) {
            for (x in 0 until image.width) {
                for (y in 0 until image.height) {
                    var filtered = image.getRGB(x, y)
                    (pixelFilters as List<PixelFilter>).forEach {
                        filtered = it.apply(filtered)
                    }
                    image.setRGB(
                        x,
                        y,
                        filtered
                    )
                }
            }
        }

        rescaleOpBuilder?.let {
            image = it.build().filter(image, null)
        }
        g2d.drawImage(image, 0, 0, null)
        g2d.dispose()

        return destination
    }

    private fun rescaleOpBuilder(): RescaleOpBuilder {
        if (rescaleOpBuilder == null) {
            rescaleOpBuilder = RescaleOpBuilder()
        }
        return rescaleOpBuilder!!
    }
}
