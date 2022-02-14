import javafx.scene.layout.HBox

class DataStatisticsView (
    private val model: Model
) : HBox(), IView {

    override fun updateView() {
//        ("Not yet implemented")
    }

    init{

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}