import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class EditingBarView(
    private val model: Model
) : HBox(), IView {

    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        println("View1: updateView model.message")
        // just set the button name to the counter

    }

    init {
        // set up the view, a fixed height horizontal interface area for editing the dataset title and axes
        this.alignment = Pos.CENTER_LEFT
        padding = Insets(10.0)
        // There are 10 units of spacing around and between all toolbar widgets.
        spacing = 10.0

        children.add(Label("Title: "))
        val titleField = TextField()
        children.add(titleField)
//        titleField.text =

        children.add(Label("X-Axis: "))
        // add text field to the pane
        val xAxisField = TextField()
        children.add(xAxisField)
//        xAxisField.text =

        children.add(Label("Y-Axis: "))
        val yAxisField = TextField()
        children.add(yAxisField)
//        yAxisField.text =

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}
