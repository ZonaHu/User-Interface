import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.Stage
import kotlin.random.Random

class Main : Application() {
    // 全局变量是个数组（所有notes 数据结构 no ui）
    // 刷新 函数 把Flow pane children clear掉再根据数组创建
    // background, margin, spacing

    // 弹出框 VBox 是第二个元素在stack Pane

    private val layout = BorderPane()
    // create the toolbar
    private val toolBar = HBox()
    // bottom status bar
    private val statusBar = HBox()
    // scroll pane + flow pane in the center to display notes since notes flow left to right
    private val flowPane = FlowPane()
    private val stackPane = StackPane(layout)
    // use scroll pane since we want to show a scroll bar when there are too many notes to fit height-wise
    private val scrollPane = ScrollPane(flowPane)

    override fun start(stage: Stage) {
        // ============ set the title =======================
        stage.title = "A1 Notes (z228hu)"
        // make the stage resizable
        stage.isResizable = true

        //============ add the buttons and then set button width to 100 ======================
        val addButn = Button("Add")
        addButn.prefWidth = (100.0)
        val randomButn = Button("Random")
        randomButn.prefWidth = (100.0)
        // TODO: delete is only valid if there is some selected
        val deleteButn= Button("Delete")
        deleteButn.prefWidth = (100.0)
        // TODO: clear is only valid if there is at least one note
        val clearButn= Button("Clear")
        clearButn.prefWidth = (100.0)
        val importantButn = ToggleButton("!")

        // TODO: text field can be wider
        toolBar.children.addAll(addButn, randomButn, deleteButn, clearButn, importantButn, TextField())
        // set spacing and padding
        toolBar.padding = Insets(10.0)
        toolBar.spacing = 10.0

        // =========== set up the centered scroll pane ======================================
        // hide the horizontal scroll bar for smaller size windows
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER;
        // make sure the scroll pane is fit to width
        scrollPane.isFitToWidth = true

        // ==========  handle the actions with the buttons =====================================
        // function to handle click on Random button
        addButn.setOnAction {
            addNotes()
        }

        // implement the add random function
        randomButn.setOnAction {
            // there’s about a 1 in 5 chance that the note is flagged as important
            val imp = (Random.nextInt(5) == 0)
            addRandomNotes(imp)
        }

        clearButn.setOnAction {
            // clear all notes
            clearNotes()
        }


        // the text in the status bar is initialized to 0
        statusBar.children.add(Label("0"))

        // set up the layout and the scene
        layout.top = toolBar
        layout.center = scrollPane
        layout.bottom = statusBar



        // =========== create the scene with initialized size of 800 by 600 units ===========
        val scene = Scene((stackPane), 800.0, 600.0)

        // show the scene
        stage.scene = scene
        stage.minWidth = 400.0
        stage.minHeight = 400.0
        stage.show()
    }

    private fun addNotes(){


    }

    private fun clearNotes(){
        flowPane.children.clear()
    }

    // function to add the notes
    private fun addRandomNotes(importantFlg: Boolean){
        val title = Label(genParagraph().first)
        val body = Label(genParagraph().second).apply { this.isWrapText = true}
        val notes = VBox()
        // set the background colors for the notes
        if (!importantFlg){
            notes.style = "-fx-background-color:WHITE"
        }else{
            notes.style = "-fx-background-color:LIGHTYELLOW"
        }
        // set the sizes as 150 by 200 unit rectangular areas
        notes.prefWidth = 150.0
        notes.prefHeight = 200.0
        // 10 unit space between the title and body.
        notes.spacing = 10.0
        // There’s a 10-unit margin inside the note rectangle
        notes.padding = Insets(10.0)

        notes.children.addAll(title, body)
        // TODO: save current notes to a data structure
        addToFlowPane(notes)
    }

    private fun addToFlowPane(notes: VBox) {
        flowPane.padding = Insets(10.0)
        flowPane.vgap = 10.0
        flowPane.hgap = 10.0
        flowPane.children.add(notes)
    }

    // function to update the status bar
    // TODO: 全局变量 onclick 的时候handel
    private fun updateStatus(statusText: String){
        statusBar.children.clear()
        statusBar.children.add(Label(statusText))
    }
}

