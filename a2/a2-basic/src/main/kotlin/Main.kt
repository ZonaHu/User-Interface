import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.*
import javafx.stage.Stage

// starter code from cs349 w22 public repo, MVC2 sample code
// according to Piazza @248, we do not have to create a separate controller, the view can mutate the model.
class Main : Application() {

    // create and initialize the Model
    private val model = Model()

    override fun start(stage: Stage) {

        // ============ set the window name =======================
        stage.title = "A2 Grapher (z228hu)"
        // make the stage resizable
        stage.isResizable = true

        // the main interface
        val main = BorderPane()
        // set up the border color
        main.style = "-fx-border-color: lightgrey"
        val layout = BorderPane()

        main.top = EditingBarView(model)

        // create each view, and tell them about the model
        // the views will register themselves with the model

        // a 150 unit wide area for the data table
        main.left = DataTableView(model)
        // a centre WHITE area for the graph
        main.center = GraphView(model)
        // a 125 unit wide right side area for data statistics
        main.right = DataStatisticsView(model)

        layout.top = ToolBarView(model)
        layout.center = main
        // lightgrey status bar at the bottom
        layout.bottom = StatusBarView(model)

        // =========== create the scene with initialized size of 800 by 600 units ===============================
        // Add layout to a scene (and the scene to the stage)， initial size 800 by 600
        val scene = Scene(layout, 800.0, 600.0)

        // ============================ show the scene===========================================================
        stage.scene = scene
        // can be resized with a minimum size of 600 by 400 units
        stage.minWidth = 600.0
        stage.minHeight = 400.0
        stage.show()
    }
}

