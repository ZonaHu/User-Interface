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

class PlayerBoardView (private val model: Game): VBox(), IView {

    private val title = Label("My Formation")
    private val grid = GridPane()
    private val hbox = HBox(title)

    override fun updateView() {
        // reset the children to update the statistics
        children.clear()
        children.add(hbox)
        children.add(grid)
        var rStart = 'A'
        var cnt = 0
        // reference: https://git.uwaterloo.ca/j2avery/cs349-public/-/blob/master/03.JavaFX/04.gridpane/src/main/kotlin/Main.kt
        for (i in 0..11) {
            for (j in 0..11) {
                if ((j == 0) || (j == 11)){
                    if ((i!=0)&&(i!=11)){
                        // add Column label
                        val cNum = Label(i.toString())
                        cNum.alignment = Pos.CENTER
                        grid.add(cNum, i, j)
                    }
                }else if ((i == 0) || (i == 11)){
                        // add row label
                        if (rStart == 'K'){ // reset row label to A for the last column
                            rStart = 'A'
                        }
                        val rNum = Label(rStart.toString())
                        rNum.alignment = Pos.CENTER
                        grid.add(rNum, i, j)
                        rStart++
                }
                else{
                    val rect = Rectangle(30.0, 30.0)
                    rect.style = "-fx-fill: lightblue; -fx-stroke: black; -fx-stroke-width: 1;";
                    // use (x,y) to store the grid position of each rectangle
                    // we're taking advantage of the fact that these fields aren't being otherwise used
                    rect.x = i.toDouble()
                    rect.y = j.toDouble()
                    grid.add(rect, i, j)
                }
            }
        }

        println("update player board")
    }

    init{
//        style = "-fx-background-color:LIGHTYELLOW"
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
//        grid.style =    "-fx-background-color:BLACK"
        // add to the model when we're ready to start receiving data
        model.addView(this)
    }
}