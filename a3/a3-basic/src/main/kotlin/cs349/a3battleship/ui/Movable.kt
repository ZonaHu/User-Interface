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
    private var shiptype = ShipType.Battleship // the default ship type
    private var orientation = Orientation.VERTICAL // the default orientation
    private var counter = 0

    init {
        // important that this is in bubble phase, not capture phase
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null) {
                val x = 9-(-(node.translateX+58.0+counter*30.0)/30.0)
                val y = (node.translateY-20.0)/30.0
                val cell = Cell(x.roundToInt(), y.roundToInt())
                // TODO: if it has been placed, we can place it again somewhere valid

                // debug statements
                println(x)
                println(y)
                println("Here!")
                if ((model.placeShip(Player.Human, shiptype, orientation, cell) == null)){
//                 req 13: if the ship is placed partially or fully outside the Player Board
//                 or overlaps another ship,
//                 it will return to its original position in the Player Navy.
                    node.translateX = 0.0
                    node.translateY = 0.0
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

    fun makeMovable(node: Node, ship: ShipType, cnt: Int) {
        node.onMouseClicked = EventHandler { e ->
            if (movingNode == null) {
                println("click '$node'")
                this.movingNode = node
                shiptype = ship
                counter = cnt
                offsetX = node.translateX - e.sceneX
                offsetY = node.translateY - e.sceneY
                // we don't want to drag the background too
                e.consume()
            }
        }
    }
}
