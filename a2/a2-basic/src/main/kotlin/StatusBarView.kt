import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.HBox

class StatusBarView (
    private val model: Model
): HBox(), IView {

    override fun updateView() {
        // check if a note is selected

        // the message is super easy, just ask the model
        message.text = "${model.message}"
    }

    val message = Label("")

    init {
        this.minHeight = 40.0
        // status bar layout
//        alignment = Pos.CENTER_LEFT
        padding = Insets(10.0)
        spacing = 30.0
        style = "-fx-background-color:LIGHTGRAY"

        // add to the model when we're ready to start receiving data
        model.addView(this)
    }
}