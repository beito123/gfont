package com.gmx.mattcha.gfont.core

/*
  gfont

  Copyright (c) 2019 beito

  This software is released under the MIT License.
  http://opensource.org/licenses/mit-license.php
*/

import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class FontGenerator(var drawers : MutableList<CharDrawer>,
                    val options : FontOptions = FontOptions()
) {

    private fun getSupportedDrawer(c : Char) : CharDrawer? {
        for (d in drawers) {
            if (d.canDisplay(c)) {
                return d
            }
        }

        return null
    }

    fun generateFont(ids : List<Short>) : Map<Short, BufferedImage> {
        val map = mutableMapOf<Short, BufferedImage>()

        val width = (this.options.pixel * this.options.count) * this.options.scale
        val height = (this.options.pixel * this.options.count) * this.options.scale

        val hints = mutableMapOf<RenderingHints.Key, Any>()

        if (this.options.enabledAntialiasing) {
            hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)
        }

        val workspace = BufferedImage(width / this.options.count, height / this.options.count, BufferedImage.TYPE_INT_ARGB)

        for (i in ids) {
            val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

            val dst = img.createGraphics()

            val start = (i.toInt() shl 8)
            val chars = getChars(
                start,
                start + (this.options.count * this.options.count),
                this.options.defaultChar
            )
            if (!this.options.generateEmptyGlyph) { // avoids generating empty glyph
                var changed = false
                for ((uni, c) in chars.withIndex()) {
                    if ((this.options.defaultChar.toInt() == uni || c != this.options.defaultChar) &&
                        this.getSupportedDrawer(c) != null) {
                        changed = true
                        break
                    }
                }

                if (!changed) {
                    continue // skip
                }
            }

            this.drawGlyph(dst, workspace, chars, hints)

            dst.dispose()

            map[i] = img
        }

        return map
    }

    fun drawGlyph(dst : Graphics2D, workspace : BufferedImage, chars : List<Char>, hints : Map<RenderingHints.Key, Any>? = null) {
        val size = this.options.pixel * this.options.scale

        val g2d = workspace.createGraphics()

        g2d.color = this.options.color
        g2d.background = this.options.background

        if (hints !== null) {
            g2d.setRenderingHints(hints)
        }

        for ((i, c) in chars.withIndex()) {
            val x = i % this.options.count
            val y = i / this.options.count

            g2d.clearRect(0, 0, size, size)

            val drawer = this.getSupportedDrawer(c) ?: continue

            drawer.drawChar(g2d, c, size)

            dst.drawImage(workspace, null, x * size, y * size)
        }

        g2d.dispose()
    }

}