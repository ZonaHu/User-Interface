import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox

class ToolBarView (private val model: Model
) : HBox(), IView {

    // The toolbar has a ChoiceBox (labelled “Dataset: ”)
    private val dropDown = ChoiceBox(FXCollections.observableArrayList(model.getDropDownMenu()))

    // a Spinner in the range from 1 to 20, starting from 1
    private val spinner = Spinner<Int>(1, 20, 1)

    override fun updateView() {
        // add the newly created name to the list of dropdown options if new names are added from other views
        if (!dropDown.items.contains(model.getCurSelect()) && model.getCurSelect() != ""){
            dropDown.items = FXCollections.observableArrayList(model.getDropDownMenu())
            // set the choice-box to select the newly created dataset name
            dropDown.selectionModel.select(model.getCurSelect())
        }
    }

    init {
        padding = Insets(10.0)
        // There are 10 units of spacing around and between all toolbar widgets.
        spacing = 10.0

        // fix the position of the dropdown menu
        dropDown.prefWidth = 100.0
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
                // add a new dataset to the model
                model.setNewDataset(spinner.value)
            }
        children.add(newBtn)

        spinner.prefWidth = 80.0
        children.add(spinner)

        model.addView(this)
    }
}