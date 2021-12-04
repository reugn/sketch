package io.github.reugn.sketch.filter

import java.awt.image.RescaleOp

class RescaleOpBuilder {

    internal var scaleFactor: Float = 1f
    internal var offset: Float = 0f

    fun build(): RescaleOp {
        return RescaleOp(scaleFactor, offset, null)
    }
}
