import javafx.scene.canvas.Canvas
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.paint.Color.hsb
import javafx.scene.text.TextAlignment

class GraphView(
    private val model: Model
) : HBox(), IView {

    // Create a canvas as a drawing surface
    private var canvas = Canvas()

    // Use the graphics context to draw on a canvas
    private val gc = canvas.graphicsContext2D

    override fun updateView() {
        style = if (!model.getSelectedTheme()) {
            // set the background color to white
            "-fx-background-color:white"
        } else {
            // set to the special theme
            "-fx-background-color:black"
        }

        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
        gc.lineWidth = 5.0
        // a rectangle with LIGHTGRAY stroke (only the top and right lines are visible)
        gc.stroke = Color.LIGHTGRAY
        gc.lineWidth = 1.5
        // 50 unit margin around the axes and rectangle.
        // Thus subtract 50*2 from the original width and height of the canvas
        // the range of the y-axis is 0 to the maximum data point value
        val inWidth = canvas.width - 100
        val inHeight = canvas.height - 100
        gc.strokeRect(50.0, 50.0, inWidth, inHeight)
        gc.stroke = if (!model.getSelectedTheme()) {
            // x- and y-axis are displayed as black lines by default
            Color.BLACK
        } else {
            // set to the white
            Color.WHITE
        }
        gc.strokeLine(50.0, 50.0, 50.0, 50.0 + inHeight)
        gc.strokeLine(50.0, 50.0 + inHeight, 50.0 + inWidth, 50.0 + inHeight)
        gc.textAlign = TextAlignment.CENTER
        gc.fill = if (!model.getSelectedTheme()) {
            // x- and y-axis are displayed as black lines by default
            Color.BLACK
        } else {
            // set to the white
            Color.LIGHTYELLOW
        }
        // the lineWidth is 5, should be accounted in calculation for the positions
        // title for the dataset, horizontally centered in the main graph area and vertically centred in the top margin.
        gc.fillText(model.getDataSet()?.title ?: "", 50.0 + inWidth / 2, 30.0)
        // x-axis label for the dataset, horizontally centred below the x-axis line and vertically centred in the bottom margi
        gc.fillText(model.getDataSet()?.xAxis ?: "", 50.0 + inWidth / 2, 80.0 + inHeight)
        gc.textAlign = TextAlignment.RIGHT
        // a “0” labelling the bottom point of the y-axis
        gc.fillText("0", 45.0, 55 + inHeight)
        // the top label on the y-axis is the maximum data point in the dataset.
        gc.fillText(model.getDataSet()?.data?.maxOrNull().toString(), 45.0, 55.0)
        gc.textAlign = TextAlignment.CENTER
        // save
        gc.save()
        // y-axis label for the dataset
        gc.translate(25.0, 50 + inHeight / 2)
        gc.rotate(270.0)
        gc.fillText(model.getDataSet()?.yAxis ?: "", 0.0, 0.0)
        gc.restore()

        val curColor = model.getCurColor()
        // adding the bars
        var color = 0.0
        val dataPointNums = model.getDataSet()?.data?.size
        var startXPos = 55.0
        if (dataPointNums != null && dataPointNums != 0) {
            val barWidth = (inWidth - 5.0) / dataPointNums - 5.0
            val colorCnt = 360.0 / dataPointNums
            // draw each data-points into bars
            for (data in model.getDataSet()!!.data) {
                val barHeight = data * (inHeight / model.getDataSet()?.data?.maxOrNull()!!)
                val verticalPos = 50 + inHeight - barHeight
                // if it's set to rainbow color
                if (curColor == Color.GRAY.toString()) {
                    // rainbow bars, saturation and brightness 1.0
                    gc.fill = hsb(color, 1.0, 1.0, 1.0)
                } else if (curColor == Color.LIGHTSKYBLUE.toString()) {
                    gc.fill = Color.LIGHTSKYBLUE
                } else if (curColor == Color.CHOCOLATE.toString()) {
                    gc.fill = Color.CHOCOLATE
                } else if (curColor == Color.LIGHTPINK.toString()) {
                    gc.fill = Color.LIGHTPINK
                } else if (curColor == Color.LIGHTSALMON.toString()) {
                    gc.fill = Color.LIGHTSALMON
                } else if (curColor == Color.LIMEGREEN.toString()) {
                    gc.fill = Color.LIMEGREEN
                }
                // the black behaviour doesn't need to be set
                gc.fillRect(startXPos, verticalPos, barWidth, barHeight)
                // update the color
                color += colorCnt
                //  a 5 unit space between the leftmost bar and the y-axis and a 5 unit space from the right-most bar and the right grey borderline
                startXPos += barWidth + 5.0
            }
        }
    }

    init {
        // To fix the canvas in the middle
        minWidth = 0.0
        minHeight = 0.0
        canvas.widthProperty().bind(widthProperty())
        canvas.heightProperty().bind(heightProperty())
        // make the canvas resizable
        canvas.widthProperty().addListener { _, _, _ ->
            updateView()
        }

        canvas.heightProperty().addListener { _, _, _ ->
            updateView()
        }

        children.add(canvas)

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }
}