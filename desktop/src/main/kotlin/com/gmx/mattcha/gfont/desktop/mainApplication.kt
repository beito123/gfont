package com.gmx.mattcha.gfont.desktop

import javafx.application.Application
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(primaryStage: Stage?) {
        if (primaryStage == null) {
            return
        }

        primaryStage.show()
    }
}