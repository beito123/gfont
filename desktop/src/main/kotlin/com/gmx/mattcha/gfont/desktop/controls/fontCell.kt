package com.gmx.mattcha.gfont.desktop.controls

import com.gmx.mattcha.gfont.desktop.FontItem
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import java.awt.Font

// Thanks. from https://stackoverflow.com/questions/42529782/listview-with-delete-button-on-every-row-in-javafx
// Questioner: A K Mishra, Answer: DVarga
class FontCell : ListCell<FontItem?>() {
    private val root = HBox()
    private val label = Label("")
    private val pane = Pane()
    private val button = Button("^")

    init {
        this.root.children.addAll(this.label, this.pane, this.button)
        HBox.setHgrow(this.pane, Priority.ALWAYS)
        button.setOnAction {
            if(index != 0) {
                listView.items.add(index - 1, item) // Add item to up
                listView.items.removeAt(index+1) // remove original
            }

            this.isFocused = true
        }
    }

    override fun updateItem(item: FontItem?, empty: Boolean) {
        super.updateItem(item, empty)
        this.text = null
        this.graphic = null

        if (item != null && !empty) {
            this.label.text = item.text

            this.graphic = root
        }
    }
}