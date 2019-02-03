package com.gmx.mattcha.gfont.core.example

/*
  gfont

  Copyright (c) 2019 beito

  This software is released under the MIT License.
  http://opensource.org/licenses/mit-license.php
*/

import java.awt.Font
import java.io.File
import javax.imageio.ImageIO

import com.gmx.mattcha.gfont.core.*

fun main() {
    val options = FontOptions()
    options.scale = 2

    val result = getBestFontSize(Font("美咲ゴシック", Font.BOLD, 26), options)

    println("Best Size:$result")

    generateBitmapFont()
}

fun generateBitmapFont() {
    val drawers = mutableListOf<CharDrawer>()

    // these are applied in order
    // so it's needed to apply Image font before normal font
    drawers.add(FontDrawer(Font("美咲ゴシック", Font.BOLD, 32))) // For scale 2
    drawers.add(FontDrawer(Font("Noto Regular", Font.BOLD, 24))) // For scale 2

    val options = FontOptions()
    options.scale = 2
    options.enabledAntialiasing = true

    val out = File("./font/")

    if (!out.exists()) {
        out.mkdirs()
    }

    val gen = FontGenerator(drawers, options)

    val ids = mutableListOf<Short>()
    for (i in 0 until 0xff) {
        ids.add(i.toShort())
    }

    val startTime = System.currentTimeMillis()

    val glyphs = gen.generateFont(ids)

    val time = System.currentTimeMillis() - startTime
    println("Complete! Time:${time}ms = ${time / 1000}s")

    for ((key, img) in glyphs) {
        val id = String.format("%02X", key)

        val file = File(out, "glyph_$id.png")

        println("i:$id, Save at ${file.name}")

        ImageIO.write(img, "png", file)
    }
}