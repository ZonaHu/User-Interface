import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import kotlin.math.roundToInt

class DataStatisticsView (
    private val model: Model
) : GridPane(), IView {
    private val numL = Label("Number")
    private val minL = Label("Minimum")
    private val maxL = Label("Maximum")
    private val avgL = Label("Average")
    private val sumL = Label("Sum")

    override fun updateView() {
        // reset the children
        children.clear()
        add(numL, 1, 0)
        add(minL, 1, 1)
        add(maxL, 1, 2)
        // The “Average” is accurate to 1 decimal place.
        add(avgL, 1, 3)
        add(sumL, 1, 4)
        add(Label(model.getDataSets()?.data?.size.toString()), 3, 0)
        add(Label(model.getDataSets()?.data?.minOrNull().toString()), 3, 1)
        add(Label(model.getDataSets()?.data?.maxOrNull().toString()), 3, 2)
        val avg = ((model.getDataSets()?.data?.average()?.times(10))?.roundToInt() ?: 0) /10.0
        add(Label(avg.toString()), 3, 3)
        add(Label(model.getDataSets()?.data?.sum().toString()), 3, 4)
    }

    init{
        // a 125 unit wide right side area for data statistics
        minWidth = 125.0
        maxWidth = 125.0
        padding = Insets(5.0)
        hgap = 5.0
        vgap = 10.0

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}