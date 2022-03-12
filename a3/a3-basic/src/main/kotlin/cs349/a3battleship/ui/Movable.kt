package cs349.a3battleship.ui

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Orientation
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.transform.Transform
import kotlin.math.roundToInt

// starter code from 349 public repo
class Movable(private val model: Game, parent: Node) {
    private var movingNode: Node? = null

    // the offset captured at start of drag
    private var offsetX = 0.0
    private var offsetY = 0.0
    private var shipType: ShipType? = null // the default ship type
    private var orientation = Orientation.VERTICAL // the default orientation
    private var counter = 0

    init {
        // important that this is in bubble phase, not capture phase
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null) {
                if (e.button == MouseButton.PRIMARY) {
                    val topLeftCoord = node.localToScene(0.0, 0.0)
                    var x = ((topLeftCoord.x-25)/30.0).roundToInt()
                    val y = ((topLeftCoord.y-50)/30.0).roundToInt()
                    if (!node.transforms.isEmpty()){
                        // modify x when it's horizontal
                        x = ((node.localToScene(node.layoutBounds.maxX, node.layoutBounds.maxY).x-25.0)/30.0).roundToInt()
                    }
                    val cell = Cell(x, y)
                    if ((model.placeShip(Player.Human, shipType!!, orientation, cell) == null)) {
//                 req 13: if the ship is placed partially or fully outside the Player Board
//                 or overlaps another ship,
//                 it will return to its original position in the Player Navy.
                        node.translateX = 0.0
                        node.translateY = 0.0
                        node.transforms.clear()
                        println("null: ")
                        println(x)
                        println(y)
                    } else {
                        println("place the rectangle")
                        println((topLeftCoord.x-25)/30.0)
                        println((topLeftCoord.y-50)/30.0)
                        // snap to grid
                        if (orientation == Orientation.HORIZONTAL){
                            node.translateX += x * 30.0 + 23.5 - node.localToScene(node.layoutBounds.maxX, node.layoutBounds.maxY).x
                            node.translateY += y * 30.0 + 52.0 - node.localToScene(0.0, 0.0).y
                        }else{
                            node.translateX += x * 30.0 + 28.0 - node.localToScene(0.0, 0.0).x
                            node.translateY += y * 30.0 + 50.0 - node.localToScene(0.0, 0.0).y
                        }
                    }
                    movingNode = null
                    shipType = null
                } else if (e.button == MouseButton.SECONDARY) {
                    orientation = if (orientation == Orientation.HORIZONTAL){
                        Orientation.VERTICAL
                    }else{
                        Orientation.HORIZONTAL
                    }
                    // Pressing the right mouse button while a ship is selected, rotation
                    val pos = node.sceneToLocal(e.sceneX, e.sceneY)
                    if (node.transforms.isEmpty()){
                        node.transforms.add(Transform.rotate(90.0, pos.x, pos.y))
                    }else{
                        node.transforms.clear()
                    }
                }
            }
        }

        parent.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            val node = movingNode
            if (node != null) {
                node.translateX = e.sceneX + offsetX
                node.translateY = e.sceneY + offsetY
                // we don't want to drag the background too
                e.consume()
            }
        }
    }

    fun makeMovable(node: Node, ship: ShipType, cnt: Int) {
        node.onMouseClicked = EventHandler { e ->
            if (e.button == MouseButton.PRIMARY &&(model.getGameState()==Game.GameState.Init || model.getGameState() == Game.GameState.SetupHuman)) {
                if (movingNode == null) {
                    println("click '$node'")
                    this.movingNode = node
                    shipType = ship
                    counter = cnt
                    orientation = Orientation.VERTICAL // the default orientation
                    // if it has been placed, we can place it again somewhere valid
                    // so, we first remove from placed ships
                    val topLeftCoord = node.localToScene(0.0, 0.0)
                    var x = ((topLeftCoord.x-25)/30.0).roundToInt()
                    val y = ((topLeftCoord.y-50)/30.0).roundToInt()
                    if (!node.transforms.isEmpty()){
                        // modify x
                        x = ((node.localToScene(node.layoutBounds.maxX, node.layoutBounds.maxY).x-25.0)/30.0).roundToInt()
                        orientation = Orientation.HORIZONTAL
                    }
                    val cell = Cell(x, y)
                    model.removeShip(Player.Human, cell)
                    offsetX = node.translateX - e.sceneX
                    offsetY = node.translateY - e.sceneY
                    // we don't want to drag the background too
                    e.consume()
                }
            }
        }
    }
}
