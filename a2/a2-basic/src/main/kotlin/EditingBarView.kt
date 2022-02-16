import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class EditingBarView(
    private val model: Model
) : HBox(), IView {
    private val titleField = TextField()
    private val xAxisField = TextField()
    private val yAxisField = TextField()

    // When notified by the model that things have changed,
    // update to display the new value
    override fun updateView() {
        // the dataset won't be null
        // just set the button name to the counter
        titleField.text = model.getDataSet()?.title.toString()
        xAxisField.text = model.getDataSet()?.xAxis.toString()
        yAxisField.text =model.getDataSet()?.yAxis.toString()
    }

    init {
        // set up the view, a fixed height horizontal interface area for editing the dataset title and axes
        this.alignment = Pos.CENTER_LEFT
        padding = Insets(10.0)
        // There are 10 units of spacing around and between all toolbar widgets.
        spacing = 10.0

        // add labels and text fields to the pane
        children.add(Label("Title: "))
        children.add(titleField)

        children.add(Label("X-Axis: "))
        children.add(xAxisField)

        children.add(Label("Y-Axis: "))
        children.add(yAxisField)

        // store text corresponding to the changes
        titleField.textProperty().addListener{ _,_,_->
            model.updateTitle(titleField.text)
        }

        xAxisField.textProperty().addListener{ _,_,_->
            model.updateX(xAxisField.text)
        }

        yAxisField.textProperty().addListener{_,_,_->
            model.updateY(yAxisField.text)
        }

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}
