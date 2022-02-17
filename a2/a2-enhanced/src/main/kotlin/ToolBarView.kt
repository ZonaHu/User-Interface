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

    // enhancement: a delete button
    private val deleteBtn = Button("Delete")
    // enhancement: a checkbox
    private val checkBox = CheckBox()

    override fun updateView() {
        deleteBtn.isDisable = false // initialized to not disabled
        // add the newly created name to the list of dropdown options if new names are added from other views
        if (!dropDown.items.contains(model.getCurSelect()) && model.getCurSelect() != ""){
            dropDown.items.add(model.getCurSelect())
        }
        if (dropDown.items.contains(model.getCurRM()) && (model.getCurSelect()!=model.getCurRM()) ){
            dropDown.items.remove(model.getCurRM())
        }
        // "increasing" is the default dataset, when it's default, delete button is disabled
        if (model.getCurSelect() == "Increasing"){
            deleteBtn.isDisable = true
        }
        // if the current selected model is changed
        // set the choice-box to select the newly selected dataset name
        dropDown.selectionModel.select(model.getCurSelect())
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
                model.setDataset(dropDown.value!!)
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
        // add the spinner
        spinner.prefWidth = 80.0
        children.add(spinner)

        // width for the new button should be 80 units
        deleteBtn.prefWidth = 80.0
        deleteBtn.onAction =
            EventHandler {
                // delete the selected dataset from the model
                model.deleteSelectedDataSet()
            }
        children.add(deleteBtn)

        //  There is another vertical Divider
        val divider2 = Separator()
        divider2.orientation = Orientation.VERTICAL
        children.add(divider2)

        children.add(Label("Theme: "))
        checkBox.setOnAction {
            model.setSelectedTheme(checkBox.isSelected)
        }
        children.add(checkBox)
        model.addView(this)
    }
}