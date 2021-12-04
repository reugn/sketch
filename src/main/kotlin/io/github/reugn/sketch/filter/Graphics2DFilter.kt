package io.github.reugn.sketch.filter

import java.awt.Graphics2D

interface Graphics2DFilter : ImageFilter {

    fun apply(g: Graphics2D)
}
