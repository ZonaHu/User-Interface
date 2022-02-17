import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox

class StatusBarView (
    private val model: Model
): HBox(), IView {

    override fun updateView() {
        // count is dataset.size
        count.text = model.getDataSets().size.toString()
    }
    private val count = Label("")

    init {
        this.minHeight = 40.0
        // status bar layout
        alignment = Pos.CENTER_LEFT
        padding = Insets(10.0)
        spacing = 5.0
        style = "-fx-background-color:LIGHTGRAY"

        children.addAll(count, Label("dataset(s)"))

        // add to the model when we're ready to start receiving data
        model.addView(this)
    }
}