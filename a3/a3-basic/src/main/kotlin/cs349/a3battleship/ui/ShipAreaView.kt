package cs349.a3battleship.ui

import cs349.a3battleship.model.Game
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class ShipAreaView(private val model: Game): VBox(), IView {

    private val title = Label("My Formation")
    private val hbox = HBox(title)

    override fun updateView() {
        println("update ship area")
    }

    init{
        style = "-fx-background-color:YELLOW"
        // 300(board) + 25(board coords) + 25(board coords) = 350 units
        minWidth = 175.0
        maxWidth = 175.0

        // add to the model when we're ready to start receiving data
        model.addView(this)
    }

}