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

const val DEFAULT_BASELINE = 1

// CharDrawer is a class to draw chars into Graphics2D
interface CharDrawer {
    fun drawChar(g2d : Graphics2D, c : Char, size : Int)
    fun canDisplay(c : Char) : Boolean
}

class FontDrawer(val font : Font, var baseline : Int = DEFAULT_BASELINE):
    CharDrawer {
    override fun canDisplay(c: Char): Boolean {
        return this.font.canDisplay(c)
    }

    override fun drawChar(g2d: Graphics2D, c : Char, size : Int) {
        g2d.font = this.font

        val metric = g2d.getFontMetrics(this.font)
        if (this.baseline == DEFAULT_BASELINE) {
            this.baseline = metric.leading + metric.ascent
        }

        g2d.drawString(c.toString(), ((size - metric.charWidth(c)) / 2), this.baseline)
    }
}

class ImageDrawer(val imgs : MutableMap<Int, BufferedImage>) : CharDrawer {
    override fun canDisplay(c: Char): Boolean {
        return this.imgs.contains(c.toInt())
    }

    override fun drawChar(g2d: Graphics2D, c: Char, size: Int) {
        g2d.drawImage(this.imgs[c.toInt()], null, 0, 0)
    }

}