import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox

class StatusBarView (
    private val model: Model
): HBox(), IView {

    override fun updateView() {
        // check if a note is selected
        count.text = "6" // dataset.size
        // the message is super easy, just ask the model
        message.text = model.message
    }

    private val message = Label("")
    private val count = Label("")

    init {
        this.minHeight = 40.0
        // status bar layout
        alignment = Pos.CENTER_LEFT
        padding = Insets(10.0)
        spacing = 5.0
        style = "-fx-background-color:LIGHTGRAY"

        children.addAll(count, message)

        // add to the model when we're ready to start receiving data
        model.addView(this)
    }
}