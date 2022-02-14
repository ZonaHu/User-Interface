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
        val layout = BorderPane(main)

        layout.top = ToolBarView(model, this)
        main.top = EditingBarView(model)

        // create each view, and tell them about the model
        // the views will register themselves with the model

        // a 150 unit wide area for the data table
        main.left = DataTableView(model)
        // a centre WHITE area for the graph
        main.center = GraphView(model)
        // a 125 unit wide right side area for data statistics
        main.right = DataStatisticsView(model)
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

//
//    // ======================================== initialization =========================================
//    private val layout = BorderPane()
//
//    // create the toolbar
//    private val toolBar = HBox()
//
//    // bottom status bar
//    private val statusBar = HBox()
//
//    // scroll pane + flow pane in the center to display notes since notes flow left to right
//    private val flowPane = FlowPane()
//    private val stackPane = StackPane(layout)
//
//    // use scroll pane since we want to show a scroll bar when there are too many notes to fit height-wise
//    private val scrollPane = ScrollPane(flowPane)
//
//    // ================================= set up the buttons============================================
//    private val addButn = Button("Add")
//    private val randomButn = Button("Random")
//    private val deleteButn = Button("Delete")
//    private val clearButn = Button("Clear")
//    private val importantButn = ToggleButton("!")
//    private val inputBox = TextField()
//
//    // ==================== initialize the counters, status strings, flags and notes list==================
//
//
//    override fun start(stage: Stage) {
//        // ============ set the title =======================
//        stage.title = "A2 Grapher (z228hu)"
//        // make the stage resizable
//        stage.isResizable = true
//
//        //============ add the buttons and then set button width to 100 =============================
//        addButn.prefWidth = (100.0)
//        randomButn.prefWidth = (100.0)
//        // delete is only valid if there is some selected
//        deleteButn.prefWidth = (100.0)
//        deleteButn.isDisable = true
//        // clear is only valid if there is at least one note
//        clearButn.prefWidth = (100.0)
//        clearButn.isDisable = true
//        // text field can be wider
//        inputBox.prefWidth = (200.0)
//
//        toolBar.children.addAll(addButn, randomButn, deleteButn, clearButn, importantButn, inputBox)
//        // set spacing and padding
//        toolBar.padding = Insets(10.0)
//        toolBar.spacing = 10.0
//
//        // ================ set up the centered scroll pane ======================================
//        // hide the horizontal scroll bar for smaller size windows
//        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
//        // make sure the scroll pane is fit to width
//        scrollPane.isFitToWidth = true
//
//        // ============== handle the actions with the buttons =====================================
//        // implement the add function
////        addButn.setOnAction {
////            addNotes(importantButn.isSelected, inputBox.text)
////        }
////
////        // immediate text search is case-insensitive according to the description
////        inputBox.textProperty()
////            .addListener { _, _, inputText -> refreshDisplay(importantButn.isSelected, inputText.lowercase()) }
//
//        // ===============================set up the default status bar =========================================
//        // a LIGHTGREY status bar at the bottom
//        statusBar.style = "-fx-background-color:LIGHTGRAY"
//        statusBar.padding = Insets(10.0)
//        statusBar.spacing = 30.0
//
//        // ===================================set up the layout and the scene====================================
//        layout.top = toolBar
//        layout.center = scrollPane
//        layout.bottom = statusBar
//
//        // =========== create the scene with initialized size of 800 by 600 units ===============================
//        val scene = Scene((stackPane), 800.0, 600.0)
//
//        // ============================ show the scene===========================================================
//        stage.scene = scene
//        // can be resized with a minimum size of 600 by 400 units
//        stage.minWidth = 600.0
//        stage.minHeight = 400.0
//        stage.show()
    }

