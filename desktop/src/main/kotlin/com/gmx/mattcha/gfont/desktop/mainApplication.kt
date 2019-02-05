package com.gmx.mattcha.gfont.desktop

import com.gmx.mattcha.gfont.desktop.controllers.MainController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class MainApplication : Application() {

    val controller = MainController()

    override fun start(primaryStage: Stage?) {
        if (primaryStage == null) {
            return
        }

        val loader = FXMLLoader(javaClass.getResource("/fxml/main.fxml"))
            .apply { setController(controller) }

        val root = loader.load<Parent>()
        root.stylesheets.add("/style/main.css")

        primaryStage.title = "gfont"
        primaryStage.isResizable = false
        primaryStage.icons.add(Image("/img/icon.png"))

        primaryStage.scene = Scene(root)
        primaryStage.show()


    }
}