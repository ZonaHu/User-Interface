package cs349.a3battleship.ui

import cs349.a3battleship.model.Game
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class ShipAreaView(private val model: Game): VBox(), IView {


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