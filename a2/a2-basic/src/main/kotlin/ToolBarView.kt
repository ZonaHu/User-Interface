import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox

class ToolBarView (private val model: Model,
                   private val controller: Main
) : HBox(), IView {

    override fun updateView() {
        // nothing
    }

    init {
        padding = Insets(10.0)
        // There are 10 units of spacing around and between all toolbar widgets.
        spacing = 10.0

        val dropDownChoices = FXCollections.observableArrayList(
            "Increasing",
            "Large",
            "Middle",
            "Single",
            "Range",
            "Percentage"
        )

        // The toolbar has a ChoiceBox (labelled “Dataset: ”)
        val dropDown = ChoiceBox(dropDownChoices)
        // fix the position of the dropdown menu

        // initialize to select the "Increasing"
        dropDown.selectionModel.selectFirst()
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
//        newBtn.onAction =
//            EventHandler { event: ActionEvent ->
//                controller.newNote()
//            }
        children.add(newBtn)

        // a Spinner
        val spinner = Spinner<Int>(1, 20, 1)
        spinner.prefWidth = 80.0
        children.add(spinner)

        model.addView(this)
    }


}