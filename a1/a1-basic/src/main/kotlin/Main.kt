import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.TextAlignment

import javafx.stage.Stage

class Main : Application() {
    private val layout = BorderPane()
    // create the tool bar
    private val toolBar = HBox()
    // bottom status bar
    private val statusBar = HBox()
    // scroll pane + flow pane in the center to display notes
    private val flowPane = FlowPane()

    override fun start(stage: Stage) {

        // ============ set the title =======================
        stage.title = "A1 Notes (z228hu)"
        // make the stage resizable
        stage.isResizable = true

        //============ add the buttons and then set button width to 100 ===========
        val addButn = Button("Add")
        addButn.prefWidth = (100.0)
        val randomButn = Button("Random")
        randomButn.prefWidth = (100.0)
        val deleteButn= Button("Delete")
        deleteButn.prefWidth = (100.0)
        val clearButn= Button("Clear")
        clearButn.prefWidth = (100.0)
        val importantButn = ToggleButton("!")

        // =========== create the scene with initialized size of 800 by 600 units ===========
        val scene = Scene((layout), 800.0, 600.0)
        // TODO: text field can be wider
        toolBar.children.addAll(addButn, randomButn, deleteButn, clearButn, importantButn, TextField())
        // set spacing and padding
        toolBar.padding = Insets(10.0)
        toolBar.spacing = 10.0


        val scrollPane = ScrollPane(flowPane)
        // hide the horizontal scroll bar for smaller size windows
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER;
        // make sure the scroll pane is fit to width
        scrollPane.isFitToWidth = true

        addNotes(false)
        addNotes(false)
        addNotes(true)
        addNotes(false)
        addNotes(true)
        addNotes(false)
        addNotes(false)
        addNotes(false)
        addNotes(false)

        // 全局变量是个数组（所有notes 数据结构 no ui）
        // 刷新 函数 把Flowpane children clear掉再根据数组创建
        // background ， margin, spacing
        // 弹出框

        // the text in the status bar is initialized to 0
        statusBar.children.add(Label("0"))

        // set up the layout and the scene
        layout.top = toolBar
        layout.center = scrollPane
        layout.bottom = statusBar
//        val stackPane = StackPane(layout)
        // 弹出框 VBox 是第二个元素在stack Pane



        // set function onclick for the buttons
        // function to handle click on Random button

        // show the scene
        stage.scene = scene
        stage.minWidth = 400.0
        stage.minHeight = 400.0
        stage.show()
    }

    // function to add the notes
    private fun addNotes(importantFlg: Boolean){
        val title = Label(genParagraph().first)
        val body = Label(genParagraph().second).apply { this.isWrapText = true}

        val notes = VBox()

        // set the background colors for the notes
        if (!importantFlg){
            notes.style = "-fx-background-color:WHITE"
        }else{
            notes.style = "-fx-background-color:LIGHTYELLOW"
        }
        // set the sizes
        notes.prefWidth = 150.0
        notes.prefHeight = 200.0
        notes.spacing = 10.0
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
    // 全局变量 unclick 的时候handel
    private fun updateStatus(statusText: String){
        statusBar.children.clear()
        statusBar.children.add(Label(statusText))
    }

}

