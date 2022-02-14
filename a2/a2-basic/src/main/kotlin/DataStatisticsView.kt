import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class DataStatisticsView (
    private val model: Model
) : GridPane(), IView {

    override fun updateView() {
//        ("Not yet implemented")
    }

    init{
        // a 125 unit wide right side area for data statistics
        minWidth = 125.0
        maxWidth = 125.0
        padding = Insets(5.0)
        hgap = 5.0
        vgap = 10.0

//        for(index, data in model.dataset.data.withIndex()){
//            this.add(Label($index), 0, index, 1, 1)
//            this.add(Spinner(),1,index,1,1)
//        }

        //  a row counter
        var r = 0
        val numL = Label("Number")
        add(numL, 1, r)
        r++

        val minL = Label("Minimum")
        add(minL, 1, r)
        r++

        val maxL = Label("Maximum")
        add(maxL, 1, r)
        r++

        val avgL = Label("Average")
        add(avgL, 1, r)
        r++

        val sumL = Label("Sum")
        add(sumL, 1, r)

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}