package cs349.a3battleship.ui

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.CellState
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import javafx.geometry.Pos
import javafx.geometry.VPos
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
    private var rStart = 'A'

    private fun createLabel(char: Char, i: Int, j: Int){
        // On the left and right of both Player and Opponent Board are row-labels, which run from “A” to “J”.
        var label = Label(rStart.toString()) // row label
        if (char == 'c'){ // column label
            // On the top and bottom of both Player and Opponent Board are column-labels, which run from “1” to “10”.
            label = Label(i.toString())
        }
        // set the default width and height to 25 units
        label.prefWidth = 25.0
        label.prefHeight = 25.0
        // font size is 12
        label.font = Font("Arial", 12.0)
        grid.add(label, i, j)
        // horizontal alignment of the label in the grid: centered
        GridPane.setFillWidth(label, true)
        label.maxWidth = Double.MAX_VALUE
        label.alignment = Pos.CENTER
        // vertical alignment of the label in the grid
        GridPane.setValignment(label, VPos.CENTER)
    }

    override fun updateView() {
        // update rectangle colors, the ones that are attacked should be changing color

        // reset the children to update the statistics
        children.clear()
        children.add(hbox)
        children.add(grid)

        // reference: https://git.uwaterloo.ca/j2avery/cs349-public/-/blob/master/03.JavaFX/04.gridpane/src/main/kotlin/Main.kt
        for (i in 0..11) {
            for (j in 0..11) {
                if ((j == 0) || (j == 11)){
                    if ((i!=0)&&(i!=11)){
                        // add Column label
                        createLabel('c', i,j)
                    }
                }else if ((i == 0) || (i == 11)){
                    // add row label
                    if (rStart == 'K'){ // reset row label to A for the last column
                        rStart = 'A'
                    }
                    createLabel('r', i,j)
                    rStart++
                }
                else{
                    val rect = Rectangle(30.0, 30.0)
                    // set color according to state，  CellState.Ocean is light blue
                    rect.style = "-fx-stroke: black; -fx-stroke-width: 1;"
                    // use (x,y) to store the grid position of each rectangle
                    // we're taking advantage of the fact that these fields aren't being otherwise used
                    rect.x = i.toDouble()
                    rect.y = j.toDouble()
                    // LIGHTGRAY -> CellState.Attacked
                    // CORAL -> CellState.ShipHit
                    // DARKGRAY -> CellState.ShipSunk
                    val board = model.getBoard(Player.AI)
                    // LIGHTGRAY -> CellState.Attacked
                    // CORAL -> CellState.ShipHit
                    // DARKGRAY -> CellState.ShipSunk
                    when (board[j-1][i-1]) {
                        CellState.Attacked -> {
                            // if not the target:
                            rect.fill = Color.LIGHTGRAY
                        }
                        CellState.ShipHit -> {
                            rect.fill = Color.CORAL
                        }
                        CellState.ShipSunk -> {
                            rect.fill = Color.DARKGRAY
                        }
                        else -> { rect.fill = Color.LIGHTBLUE}
                    }
                    rect.setOnMouseClicked {
                        if (model.getGameState()== Game.GameState.FireHuman) {
                            model.attackCell(Cell(i - 1, j - 1))
                        }
                    }
                    grid.add(rect, i, j)
                }
            }
        }
        println("update player board")
    }

    init{
        // 300(board) + 25(board coords) + 25(board coords) = 350 units
        minWidth = 350.0
        maxWidth = 350.0
        // label is 25 units high
        hbox.prefHeight = 25.0

        // set up the font for board label, size 16 and bold
        title.font = Font("Arial", 16.0)
        title.style = "-fx-font-weight: bold"
        hbox.alignment = Pos.CENTER

        //  The size of the Player Board must be 300 x 300 units
        // and 2 board coords = 2 * 25  = 50 units
        grid.minWidth = 350.0
        grid.minHeight = 350.0
        grid.maxWidth = 350.0
        grid.maxHeight = 350.0

        // add to the model when we're ready to start receiving data
        model.addView(this)
    }
}