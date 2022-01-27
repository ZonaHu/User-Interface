import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Stage

class Main : Application() {
    private val layout = BorderPane()

    override fun start(stage: Stage) {
//
//        The application opens as a window named “A1 Notes (userid)” with a toolbar at the top and a status bar at the bottom. “Userid” is your UWaterloo user id.
//        The initial size of the application part of the window is 800 by 600 units when opened.
//        The layout is “responsive” with a minimum size of 400 by 400 units.
//
//        The toolbar has 4 buttons (Add, Random, Delete, Clear), a smaller toggle-button labeled (“!”), and an editable text field for searching.

//         set the title
        stage.title = "A1 Notes (z228hu)"

//         create the tool bar
        val toolBar = ToolBar()
        val addButn = Button("Add")
        val randomButn = Button("Random")
        val deleteButn= Button("Delete")
        val clearButn= Button("Clear")
        val importantButn = Button("!")

        val scene = Scene((layout), 800.0, 600.0)
        toolBar.items.addAll(addButn, randomButn, deleteButn, clearButn, importantButn)

        val topPart = HBox(toolBar)  //        topPart.children.add(toolBar)


//        val statusBar
//        val bottomPart = HBox()
//        bottomPart.children.add()

        layout.top = topPart
//        layout.bottom = bottomPart
        // show the scene
        stage.scene = scene
        stage.show()
    }
}