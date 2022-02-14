import javafx.scene.layout.HBox

class GraphView (
    private val model: Model
) : HBox(), IView {

    override fun updateView() {
//        ("Not yet implemented")
    }

    init{
        style = " -fx-background-color:red"




        // register with the model when we're ready to start receiving data
        model.addView(this)
    }


}