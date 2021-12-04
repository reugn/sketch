package io.github.reugn.sketch.filter

class BrightnessFilter(private val brightness: Float) : RescaleOpFilter {

    override fun apply(builder: RescaleOpBuilder) {
        require(brightness in 0.0..1.0) {
            throw IllegalArgumentException("Invalid brightness ratio")
        }
        builder.offset = translateRatio()
    }

    private fun translateRatio(): Float {
        if (brightness == 0.5f) {
            return 0f
        } else if (brightness > 0.5) {
            return (brightness - 0.5f) * 512f
        }
        return -(brightness + 0.5f) * 512f
    }
}
