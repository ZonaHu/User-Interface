import javafx.scene.layout.GridPane

class GraphView (
    private val model: Model
) : GridPane(), IView {

    override fun updateView() {
//        ("Not yet implemented")
    }

    init{
        // set the background color to white
        style = " -fx-background-color:white"




        // register with the model when we're ready to start receiving data
        model.addView(this)
    }


}