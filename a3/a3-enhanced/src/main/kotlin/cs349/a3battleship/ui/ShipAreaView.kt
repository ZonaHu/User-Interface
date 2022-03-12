package cs349.a3battleship.ui

import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Window

class ShipAreaView(private val model: Game, private val mover: Movable): VBox(), IView {
    // the player fleet
    private val title = Label("My Fleet")
    private val titleHBox = HBox(title)
    private val myShipArea = HBox()
    private val opFleet = Label("Opponent's Fleet")
    private val opFleetHBox = HBox(opFleet)
    private val opShipArea = HBox()
    // game controls: two buttons at the bottom
    private val startButn = Button("Start Game")
    private val exitButn = Button("Exit Game")
    private var cnt = 0
    var shipMap: MutableMap<ShipType, Rectangle> = mutableMapOf()
    var opShipMap: MutableMap<ShipType, Rectangle> = mutableMapOf()

    private val palette: Array<Color> = arrayOf<Color>(Color.CHOCOLATE, Color.PINK, Color.ORANGE,  Color.LIMEGREEN, Color.BLUE)

    private fun createShips(){
        // update the position of the ships
        for (ship in model.getShipsToPlace()){
            // draw rectangles with length corresponding to the shiptype
            val width = 23.0
            val height = 30.0 * model.getShipLength(ship)
            val rect = Rectangle(width, height)
            rect.style = "-fx-stroke: black; -fx-stroke-width: 3;"
            rect.fill = palette[cnt]
            rect.opacity = 0.5
            // req.12, Clicking on a ship in the Player Fleet with the left mouse button selects it.
            mover.makeMovable(rect, ship, cnt)
            shipMap[ship] = rect
            cnt++
            myShipArea.children.add(rect)
        }
    }

    private fun getOpShips(){
        // update the position of the ships
        for (ship in model.getShipsToPlace()){
            // draw rectangles with length corresponding to the shiptype
            val width = 23.0
            val height = 20.0 * model.getShipLength(ship)
            val rect = Rectangle(width, height)
            rect.style = "-fx-stroke: black; -fx-stroke-width: 1;"
            // if the ship is sunk
            rect.fill = Color.TRANSPARENT
            opShipMap[ship] = rect
            opShipArea.children.add(rect)
        }
        model.getShipsIsSunk(Player.AI).forEach() { ship->
            opShipMap[ship.shipType]?.let {
                // if the ship is sunk, set to dark gray
                it.fill = Color.DARKGRAY
            }
        }
    }

    private fun resetShips(){
        myShipArea.children.clear()
        opShipArea.children.clear()
        children.clear()
        cnt = 0
        createShips()
        getOpShips()
        children.addAll(titleHBox,myShipArea,opFleetHBox, opShipArea, startButn,exitButn)
    }

    override fun updateView() {
        //  req 17, start game will be enabled when all ships are placed on the board
        startButn.isDisable =
            !(model.getShipsPlacedCount(Player.Human) == 5 && (model.getGameState()==Game.GameState.Init || model.getGameState() == Game.GameState.SetupHuman))
        if (model.getGameState() == Game.GameState.WonAI || model.getGameState() == Game.GameState.WonHuman){
            cnt = 0
            if (model.getGameState()== Game.GameState.WonAI){
                title.text = "You were defeated!"
            }else {
                title.text = "You Won!"
            }
            // All ships of the player that have been sunk remain on the Player Board, all other ships return to their original location in the Player Fleet
            model.getShipsIsNotSunk(Player.Human).forEach() { ship->
                shipMap[ship.shipType]?.let { mover.newPlacement(it) }
            }
        }
        opShipArea.children.clear()
        getOpShips()
    }

    init{
        // 300(board) + 25(board coords) + 25(board coords) = 350 units
        minWidth = 175.0
        maxWidth = 175.0
        padding = Insets(0.0, 5.0, 0.0, 5.0 )

        title.font = Font("Arial", 16.0)
        title.style = "-fx-font-weight: bold"
        title.alignment = Pos.CENTER

        titleHBox.prefHeight = 25.0
        titleHBox.alignment = Pos.CENTER

        myShipArea.padding = Insets(5.0)
        myShipArea.spacing = 5.0
        myShipArea.prefHeight = 150.0

        opFleet.font = Font("Arial", 14.0)
        opFleet.style = "-fx-font-weight: bold"
        opFleetHBox.prefHeight = 20.0
        opFleetHBox.alignment = Pos.CENTER

        // add ships to op's fleet area
        opShipArea.padding = Insets(5.0)
        opShipArea.spacing = 8.0
        opShipArea.prefHeight = 100.0

        startButn.prefWidth = 165.0
        // at the beginning, start is disabled as requirement 16 states,
        startButn.isDisable = true

        exitButn.prefWidth = 165.0
        // exit the game, req 22
        exitButn.setOnAction {
            // piazza @437: close the window and exit the program
            // only need to support single round of the game
            val stage: Window = this.scene.window
            stage.hide()
        }

        // req 18, start game now can be clicked to start the game,
        startButn.setOnAction {
            model.startGame()
            startButn.isDisable = true
            // After this, the location and orientation of ships cannot be altered anymore.
            // now gameState now becomes Game.GameState.SetupAI
        }
        resetShips()
        // add to the model when we're ready to start receiving data
        model.addView(this)
    }
}