import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.HBox


class ToolBarView (private val model: Model,
                   private val controller: Main
) : HBox(), IView {

    override fun updateView() {
        // nothing
    }

    init {
        padding = Insets(10.0)
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
        // initialize to select the "Increasing"
        dropDown.selectionModel.selectFirst()
        children.add(Label("Dataset: "))
        children.add(dropDown)

        // a New button,
        val newBtn = Button("New")
        newBtn.prefWidth = 100.0
//        newBtn.onAction =
//            EventHandler { event: ActionEvent ->
//                controller.newNote()
//            }
        children.add(newBtn)

        //
        // and a Spinner


        model.addView(this)
    }


}