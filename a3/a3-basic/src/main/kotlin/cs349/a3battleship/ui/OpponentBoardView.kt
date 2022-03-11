package cs349.a3battleship.ui

import cs349.a3battleship.model.Game
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font


class OpponentBoardView (private val model: Game): VBox(), IView {

    private val title = Label("Opponent's Formation")
    private val grid = GridPane()
    private val hbox = HBox(title)

    override fun updateView() {
        // reset the children to update the statistics
        children.clear()
        children.add(hbox)
        children.add(grid)
        for (i in 0..9) {
            for (j in 0..9) {
                val rect = Rectangle(30.0, 30.0)
                rect.fill = Color.WHITE
                // use (x,y) to store the grid position of each rectangle
                // we're taking advantage of the fact that these fields aren't being otherwise used
                rect.x = i.toDouble()
                rect.y = j.toDouble()
                grid.add(rect, i, j)
            }
        }
        println("update player board")
    }

    init{
        style = "-fx-background-color:LIGHTYELLOW"
        // 300(board) + 25(board coords) + 25(board coords) = 350 units
        minWidth = 350.0
        maxWidth = 350.0
        padding = Insets(5.0, 0.0, 0.0,0.0)
        //  The size of the Player Board must be 300 x 300 units
        // and 2 board coords = 2 * 25  = 50 units
        grid.minWidth = 350.0
        grid.minHeight = 350.0
        grid.maxWidth = 350.0
        grid.maxHeight = 350.0

        // set up the font for board label
        title.font = Font("Arial", 16.0)
        title.style = "-fx-font-weight: bold"
        hbox.alignment = Pos.CENTER
        grid.style =    "-fx-background-color:BLACK"
        // add to the model when we're ready to start receiving data
        model.addView(this)
    }
}