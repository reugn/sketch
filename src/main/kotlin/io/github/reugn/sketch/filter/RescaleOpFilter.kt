package io.github.reugn.sketch.filter

interface RescaleOpFilter : ImageFilter {

    fun apply(builder: RescaleOpBuilder)
}
