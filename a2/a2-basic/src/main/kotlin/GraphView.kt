import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color

class GraphView (
    private val model: Model
) : StackPane(), IView {

    private val canvas = Canvas(250.0, 250.0)
    private val gc = canvas.graphicsContext2D


    override fun updateView() {
//        ("Not yet implemented")
    }

    init{
        // set the background color to white
        style = " -fx-background-color:white"

        // Create a scene graph with a root node that holds the objects that we want to display on the stage
        val root = Group()

        // Create a canvas as a drawing surface
        var canvas = Canvas()

        // Use the graphics context to draw on a canvas
        val gc = canvas.graphicsContext2D
        gc.lineWidth = 5.0
        gc.stroke = Color.WHITE
        gc.fill = Color.WHITE
        gc.fillRect(75.0, 75.0, 100.0, 100.0)
        gc.fill = Color.AQUA
        gc.fillRect(100.0, 100.0, 100.0, 100.0)
        gc.fill = Color.YELLOWGREEN
        gc.fillRect(125.0, 125.0, 100.0, 100.0)

        // Add the canvas to the scene
        root.children.add(canvas)

        children.add(root)
        // register with the model when we're ready to start receiving data
        model.addView(this)
    }


}