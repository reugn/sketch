package io.github.reugn.sketch.filter

class ContrastFilter(private val contrast: Float) : RescaleOpFilter {

    override fun apply(builder: RescaleOpBuilder) {
        require(contrast in 0.0..1.0) {
            throw IllegalArgumentException("Invalid contrast ratio")
        }
        builder.scaleFactor = translateRatio()
    }

    private fun translateRatio(): Float {
        return contrast * 2
    }
}
