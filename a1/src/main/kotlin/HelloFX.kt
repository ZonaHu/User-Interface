import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.text.*
import javafx.stage.Stage

class HelloFX : Application() {

    override fun start(stage: Stage) {
        val label = Label("Hello JavaFX")
        label.font = Font("Helvetica", 14.0)
        val scene = Scene(StackPane(label), 320.0, 240.0)
        stage.title = "HelloFX"
        stage.scene = scene
        stage.show()
    }
}