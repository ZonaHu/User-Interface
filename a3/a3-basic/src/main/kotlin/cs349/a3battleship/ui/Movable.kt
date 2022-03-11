package cs349.a3battleship.ui
import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Orientation
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import kotlin.math.roundToInt

// code from 349 public repo
class Movable(private val model: Game, parent: Node) {
    private var movingNode: Node? = null

    // the offset captured at start of drag
    private var offsetX = 0.0
    private var offsetY = 0.0
    // save the coordinates at the start
    private var startX = 0.0
    private var startY = 0.0
    private var shiptype = ShipType.Battleship // the default ship type
    private var orientation = Orientation.VERTICAL // the default orientation

    init {
        // important that this is in bubble phase, not capture phase
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null) {
                println("drop '$node'")
                // req 13: if the ship is placed partially or fully outside of the Player Board
                // or overlaps another ship,
                // it will return to its original position in the Player Navy.
                println(node.translateX)
                println(node.translateY)
                println(9-(-(node.translateX+55.0)/30.0).roundToInt())
                println((((node.translateY-10.0)/30.0).roundToInt()))
                println("done!")
                // defualt cell
                var cell = Cell(9-(-(node.translateX+55.0)/30.0).roundToInt(), ((node.translateY-10.0)/30.0).roundToInt())
                if (model.placeShip(Player.Human, shiptype, orientation, cell) == null){
//                    node.translateX = startX
//                    node.translateY = startY
                    println(node.translateX)
                    println(node.translateY)
                    println(9-(-(node.translateX+55.0)/30.0).roundToInt())
                    println((((node.translateY-10.0)/30.0).roundToInt()))
                    println("done!")
                }
                movingNode = null
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

    fun makeMovable(node: Node, ship: ShipType) {
        node.onMouseClicked = EventHandler { e ->
            if (movingNode == null) {
                println("click '$node'")
                this.movingNode = node
                shiptype = ship
                startX = node.translateX
                startY = node.translateY
                offsetX = node.translateX - e.sceneX
                offsetY = node.translateY - e.sceneY
                // we don't want to drag the background too
                e.consume()
            }
        }
    }
}
