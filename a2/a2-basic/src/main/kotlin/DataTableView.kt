import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Spinner
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox

fun createRow (text: String, default: Int): HBox {
    // set label properties

    // add label widget to the pane
    val hBox = HBox();
    val label = Label()
    label.text = text

    val spinner = Spinner<Int>(1, 100, default)
    spinner.isEditable = false
    spinner.prefWidth = 70.0

    hBox.spacing = 10.0
    hBox.prefWidth = 100.0
    hBox.alignment = Pos.CENTER_RIGHT

    hBox.children.addAll(label, spinner);
    return hBox;
}

internal class DataTableView(
    private val model: Model
) : ScrollPane(), IView {
    private val flowPane = FlowPane()
    private var counter = 0


    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        println("View2: updateView")
        flowPane.children.clear()
        counter = 0
        for (index in model.getDataSets()?.data!!){
            counter ++
            flowPane.children.add(createRow("$counter: ", index))
        }
    }

    init {
        minWidth = 150.0
        maxWidth = 150.0
        // hide the horizontal scroll bar for smaller size windows
        hbarPolicy = ScrollBarPolicy.NEVER
        // make sure the scroll pane is fit to width
        isFitToWidth = true
        padding = Insets(10.0)

        flowPane.alignment = Pos.CENTER_LEFT
        flowPane.hgap = 10.0
        flowPane.prefHeight = 100.0

        content = flowPane
        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}
