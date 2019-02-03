package com.gmx.mattcha.gfont.core

/*
  gfont

  Copyright (c) 2019 beito

  This software is released under the MIT License.
  http://opensource.org/licenses/mit-license.php
*/

import java.awt.Font
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.math.abs

fun getChars(start : Int, count : Int, def : Char = ' ') : List<Char> {
    val list = mutableListOf<Char>()

    for (i in start until count) {
        val c = i.toChar()
        if (c.isDefined()) {
            list.add(c)
        } else {
            list.add(def)
        }
    }

    return list
}

// getBestFontSize finds out the best size of font
fun getBestFontSize(font : Font, options : FontOptions) : Int {
    val width = options.pixel * options.scale
    val height = width

    val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g2d = img.createGraphics()

    val baseSize = options.pixel * options.scale

    // Decide which it adjusts first
    val firstHeight = getFontHeight(g2d, font.deriveFont(baseSize.toFloat()))
    val diff = height - firstHeight
    val adjust = when {
        diff < 0 -> // too bigger
            -1

        diff > 0 -> // too smaller
            1

        else -> // just size!
            return baseSize
    }

    var oldSize = baseSize
    var oldDiff = abs(diff)
    for (i in 0 until 10) { // tries for 10 times
        val s = oldSize + adjust

        val f = font.deriveFont(s.toFloat())
        val h = getFontHeight(g2d, f)
        val d = abs(height - h)
        if (d > 0) {
            if (d > oldDiff) { // if diff is bigger than older result, returns older size.
                return oldSize
            }

            // if diff is smaller than older result, try to continue.
        } else {
            return s
        }

        oldSize = s
        oldDiff = d
    }

    return oldSize
}

private fun getFontHeight(g2d : Graphics2D, font: Font) : Int {
    val metrics = g2d.getFontMetrics(font)

    return metrics.maxAscent + metrics.maxDescent + metrics.leading
}