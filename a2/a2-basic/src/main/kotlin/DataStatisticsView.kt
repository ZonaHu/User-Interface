import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox

class DataStatisticsView (
    private val model: Model
) : HBox(), IView {

    override fun updateView() {
//        ("Not yet implemented")
    }

    init{
        // a 125 unit wide right side area for data statistics
        width = 125.0
        style = " -fx-background-color:black"

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}