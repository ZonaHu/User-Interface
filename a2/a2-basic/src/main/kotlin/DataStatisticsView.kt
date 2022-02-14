import javafx.scene.control.ScrollPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class DataStatisticsView (
    private val model: Model
) : FlowPane(), IView {

    override fun updateView() {
//        ("Not yet implemented")
    }

    init{
        // a 125 unit wide right side area for data statistics
        width = 125.0
        maxWidth = 125.0
//        style = " -fx-background-color:black"

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}