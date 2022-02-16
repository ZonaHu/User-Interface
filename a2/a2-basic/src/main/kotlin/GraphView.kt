import javafx.scene.canvas.Canvas
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment

class GraphView (
    private val model: Model
) : HBox(), IView {

    // Create a canvas as a drawing surface
    private var canvas = Canvas()
    // Use the graphics context to draw on a canvas
    private val gc = canvas.graphicsContext2D

    override fun updateView() {
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
        gc.lineWidth = 5.0
        // a rectangle with LIGHTGRAY stroke (only the top and right lines are visible)
        gc.stroke = Color.LIGHTGRAY
        gc.lineWidth = 1.5
        // 50 unit margin around the axes and rectangle.
        // Thus subtract 50*2 from the original width and height of the canvas
        val inWidth = canvas.width - 100
        val inHeight = canvas.height - 100
        gc.strokeRect(50.0, 50.0, inWidth, inHeight)
        // x- and y-axis are displayed as black lines
        gc.stroke = Color.BLACK
        gc.strokeLine(50.0, 50.0, 50.0, 50.0 + inHeight)
        gc.strokeLine(50.0, 50.0 + inHeight, 50.0 + inWidth, 50.0 + inHeight)
        gc.textAlign = TextAlignment.CENTER
        // title for the dataset
        gc.fillText(model.getDataSet()?.title ?: "", 50.0 + inWidth/2, 25.0 )
        // x-axis label for the dataset
        gc.fillText(model.getDataSet()?.xAxis ?: "", 50.0 + inWidth/2, 75.0 + inHeight)
        gc.textAlign = TextAlignment.RIGHT
        // a “0” labelling the bottom point of the y-axis
        gc.fillText("0", 45.0, 55 + inHeight)
        // the top label on the y-axis is the maximum data point in the dataset.
        gc.fillText(model.getDataSet()?.data?.maxOrNull().toString(), 45.0, 55.0)
        gc.textAlign = TextAlignment.CENTER
        // save
        gc.save()
        // y-axis label for the dataset
        gc.translate(25.0, 50 + inHeight/2)
        gc.rotate(270.0)
        gc.fillText(model.getDataSet()?.yAxis ?: "", 0.0, 0.0)
        gc.restore()
    }

    init{
        // set the background color to white
        style = " -fx-background-color:white"

//        // Create a scene graph with a root node that holds the objects that we want to display on the stage
//        val root = Group()
        spacing = 50.0
        // To fix the canvas in the middle
        minWidth = 0.0
        minHeight = 0.0
        canvas.widthProperty().bind(widthProperty())
        canvas.heightProperty().bind(heightProperty())

        canvas.widthProperty().addListener{ _,_, _->
            updateView()
        }

        canvas.heightProperty().addListener{ _,_, _->
            updateView()
        }

        children.add(canvas)

//        // Add the canvas to the scene
//        root.children.add(canvas)
//
//        children.add(root)
        // register with the model when we're ready to start receiving data
        model.addView(this)
    }


}