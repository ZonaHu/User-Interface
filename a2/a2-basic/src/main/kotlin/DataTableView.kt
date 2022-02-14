import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Spinner
import javafx.scene.control.TextArea
import javafx.scene.layout.FlowPane

internal class DataTableView(
    private val model: Model
) : FlowPane(), IView {

    private val text = TextArea("")

    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        println("View2: updateView")

    }

    init {
        maxWidth = 150.0
//        style = " -fx-background-color:black"
        padding = Insets(20.0)
        vgap = 10.0
        hgap = 10.0

        // set label properties

        // add label widget to the pane
        val title1 = Label("1: ")
        this.children.add(title1)

        // a Spinner
        val spinner = Spinner<Int>(1, 20, 1)
        spinner.prefWidth = 80.0
        this.children.add(spinner)

        // add label widget to the pane
        val title2 = Label("2: ")
        this.children.add(title2)

        // a Spinner
        val spinner2 = Spinner<Int>(1, 20, 1)
        spinner2.prefWidth = 80.0
        this.children.add(spinner2)

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}
