package io.github.reugn.sketch.transformer

import java.awt.image.BufferedImage

interface ImageTransformer {

    fun transform(image: BufferedImage): BufferedImage
}
