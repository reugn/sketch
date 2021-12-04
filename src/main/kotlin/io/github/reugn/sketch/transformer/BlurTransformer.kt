package io.github.reugn.sketch.transformer

import java.awt.image.BufferedImage
import java.awt.image.ConvolveOp
import java.awt.image.Kernel

class BlurTransformer(private val radius: Int) : ImageTransformer {

    override fun transform(image: BufferedImage): BufferedImage {
        val weight = 1.0f / (radius * radius)
        val kernelData = FloatArray(radius * radius)
        for (i in kernelData.indices) {
            kernelData[i] = weight
        }

        val kernel = Kernel(radius, radius, kernelData)
        val op = ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null)

        return op.filter(image, null)
    }
}
