import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox

class ToolBarView (private val model: Model
) : HBox(), IView {

    private var dropDownChoices = FXCollections.observableArrayList(
        "Increasing",
        "Large",
        "Middle",
        "Single",
        "Range",
        "Percentage"
    )

    // a Spinner
    private val spinner = Spinner<Int>(1, 20, 1)

    override fun updateView() {
        // nothing
    }

    init {
        padding = Insets(10.0)
        // There are 10 units of spacing around and between all toolbar widgets.
        spacing = 10.0


        // The toolbar has a ChoiceBox (labelled “Dataset: ”)
        val dropDown = ChoiceBox(dropDownChoices)
        // fix the position of the dropdown menu

        // initialize to select the "Increasing"
        dropDown.selectionModel.selectFirst()
        dropDown.onAction =
            EventHandler{
                model.setDataset(dropDown.value)
        }
        // left-aligned and they do not wrap
        alignment = Pos.CENTER_LEFT
        children.add(Label("Dataset: "))
        children.add(dropDown)

        //  There is a vertical Divider between the ChoiceBox and the New Button.
        val divider = Separator()
        divider.orientation = Orientation.VERTICAL
        children.add(divider)

        // a New button
        val newBtn = Button("New")
        // width for the new button should be 80 units
        newBtn.prefWidth = 80.0
        newBtn.onAction =
            EventHandler {
                model.setNewDataset(spinner.value)
            }
        children.add(newBtn)

        spinner.prefWidth = 80.0
        children.add(spinner)

        model.addView(this)
    }


}