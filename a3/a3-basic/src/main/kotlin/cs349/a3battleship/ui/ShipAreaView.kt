package cs349.a3battleship.ui

import cs349.a3battleship.model.Game
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font

class ShipAreaView(private val model: Game): VBox(), IView {
    // the player fleet

    private val title = Label("My Fleet")
    private val hbox = HBox(title)
    private val shipArea = HBox()
    // game controls: two buttons at the bottom
    private val startButn = Button("Start Game")
    private val exitButn = Button("Exit Game")

    override fun updateView() {
        shipArea.children.clear()
        // update the position of the ships
        // add spinners for each data point
        for (ship in model.getShipsToPlace()){
            // draw rectangles with length corresponding to the shiptype
            val width = 25.0
            val height = 20.0 * model.getShipLength(ship)
            val rect = Rectangle(width, height)
            rect.style = "-fx-fill: lightyellow; -fx-stroke: black; -fx-stroke-width: 1;"
            shipArea.children.add(rect)
        }

        children.addAll(hbox,shipArea,startButn,exitButn)

        println("update ship area")
    }

    init{
        // 300(board) + 25(board coords) + 25(board coords) = 350 units
        minWidth = 175.0
        maxWidth = 175.0
        padding = Insets(0.0, 5.0, 0.0, 5.0 )

        hbox.prefHeight = 25.0
        hbox.alignment = Pos.CENTER
        title.font = Font("Arial", 16.0)
        title.style = "-fx-font-weight: bold"
        title.alignment = Pos.CENTER

        shipArea.padding = Insets(5.0)
        shipArea.spacing = 5.0
        shipArea.prefHeight = 280.0

        startButn.prefWidth = 165.0
        // at the beginning, start is disabled as requirement 16 states,
        // will be enabled when all ships are placed on the board
        startButn.isDisable = true
        exitButn.prefWidth = 165.0

        // add to the model when we're ready to start receiving data
        model.addView(this)
    }

}