package cs349.a3battleship.ui
import cs349.a3battleship.model.Game
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent

// code from 349 public repo
class Movable(private val model: Game, parent: Node) {
    private var movingNode: Node? = null

    // the offset captured at start of drag
    private var offsetX = 0.0
    private var offsetY = 0.0
    // save the coordinates at the start
    private var startX = 0.0
    private var startY = 0.0

    init {

        // important that this is in bubble phase, not capture phase
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null) {
                println("drop '$node'")
                // req 13: if the ship is placed partially or fully outside of the Player Board
                // or overlaps another ship,
                // it will return to its original positoin in the Player Navy.
//                if (model.placeShip){
//
//                }
                node.translateX = startX
                node.translateY = startY
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

    fun makeMovable(node: Node) {
        node.onMouseClicked = EventHandler { e ->
            if (movingNode == null) {
                println("click '$node'")
                this.movingNode = node
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
