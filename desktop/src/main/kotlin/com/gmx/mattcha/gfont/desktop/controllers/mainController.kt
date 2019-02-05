package com.gmx.mattcha.gfont.desktop.controllers

import com.gmx.mattcha.gfont.desktop.FontItem
import com.gmx.mattcha.gfont.desktop.controls.FontCell
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import java.awt.Font
import java.net.URL
import java.util.*

class MainController : Initializable {

    @FXML
    private lateinit var btnAddFont : Button

    @FXML
    private lateinit var btnAddImageFont : Button

    @FXML
    private lateinit var btnRemoveFont : Button

    @FXML
    private lateinit var btnGenerate : Button

    @FXML
    private lateinit var btnGenerateSub : Button

    @FXML
    private lateinit var listFonts : ListView<FontItem?>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        this.listFonts.setCellFactory { FontCell() }

        // Test data
        this.listFonts.items.add(FontItem(Font("Noto CJK Regular", 8, Font.BOLD), "A"))
        this.listFonts.items.add(FontItem(Font("Noto CJK Regular", 8, Font.BOLD), "B"))
        this.listFonts.items.add(FontItem(Font("Noto CJK Regular", 8, Font.BOLD), "C"))
        this.listFonts.items.add(FontItem(Font("Noto CJK Regular", 8, Font.BOLD), "D"))
        this.listFonts.items.add(FontItem(Font("Noto CJK Regular", 8, Font.BOLD), "E"))

        // Listen event
        this.btnAddFont.setOnAction { this.onAddFont() }
        this.btnAddImageFont.setOnAction { this.onAddImageFont() }
        this.btnRemoveFont.setOnAction { this.onRemoveFont() }
        this.btnGenerate.setOnAction { this.onGenerate() }
        this.btnGenerateSub.setOnAction { this.onGenerateSub() }
    }

    private fun onAddFont() {
        //
    }

    private fun onAddImageFont() {

    }

    private fun onRemoveFont() {

    }

    private fun onGenerate() {

    }

    private fun onGenerateSub() {

    }
}