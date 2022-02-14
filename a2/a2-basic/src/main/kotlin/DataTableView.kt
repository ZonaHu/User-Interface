import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

internal class DataTableView(
    private val model: Model
) : GridPane(), IView {

    private val text = TextArea("")

    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        println("View2: updateView")

    }

    init {
        // set label properties

        // add label widget to the pane
        children.add(Label("1: "))

        // a Spinner
        val spinner = Spinner<Int>(1, 20, 1)
        spinner.prefWidth = 80.0
        children.add(spinner)



        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}
