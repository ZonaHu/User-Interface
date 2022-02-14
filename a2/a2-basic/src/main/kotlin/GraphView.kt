import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox

class GraphView (
    private val model: Model
) : GridPane(), IView {

    override fun updateView() {
//        ("Not yet implemented")
    }

    init{
//        maxWidth = 250.0
        style = " -fx-background-color:red"




        // register with the model when we're ready to start receiving data
        model.addView(this)
    }


}