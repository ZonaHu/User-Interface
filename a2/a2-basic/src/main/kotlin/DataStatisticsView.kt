import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.GridPane

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
        val numN = Label("0")
        val minN = Label("10")
        val maxN = Label("100")
        val avgN = Label("0")
        val sumN = Label("0")
        add(numN, 3, 0)
        add(minN, 3, 1)
        add(maxN, 3, 2)
        add(avgN, 3, 3)
        add(sumN, 3, 4)
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