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
    // list 存所有notes， 通过size()
    private var notesList = mutableListOf<Note> ()
    // counter for the number of notes existed
    private var noteCounter = 0

    override fun start(stage: Stage) {
        // ============ set the title =======================
        stage.title = "A1 Notes (z228hu)"
        // make the stage resizable
        stage.isResizable = true

        //============ add the buttons and then set button width to 100 =============================
        val addButn = Button("Add")
        addButn.prefWidth = (100.0)
        val randomButn = Button("Random")
        randomButn.prefWidth = (100.0)
        // delete is only valid if there is some selected
        val deleteButn= Button("Delete")
        deleteButn.prefWidth = (100.0)
//        deleteButn.isDisable = true
        // clear is only valid if there is at least one note
        val clearButn= Button("Clear")
        clearButn.prefWidth = (100.0)
//        clearButn.isDisable = true
        val importantButn = ToggleButton("!")

        // TODO: text field can be wider
        toolBar.children.addAll(addButn, randomButn, deleteButn, clearButn, importantButn, TextField())
        // set spacing and padding
        toolBar.padding = Insets(10.0)
        toolBar.spacing = 10.0

        // ================ set up the centered scroll pane ======================================
        // hide the horizontal scroll bar for smaller size windows
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER;
        // make sure the scroll pane is fit to width
        scrollPane.isFitToWidth = true

        // ==========  handle the actions with the buttons =====================================
        // implement the add function
        addButn.setOnAction {
            addNotes()
        }

        // function to delete a note
        deleteButn.setOnAction {
            deleteNotes()
        }

        // function to handle click on Random button
        randomButn.setOnAction {
            // there’s about a 1 in 5 chance that the note is flagged as important
            val imp = (Random.nextInt(5) == 0)
            addRandomNotes(imp)
        }

        // implement the clear all notes
        clearButn.setOnAction {
            // clear all notes
            clearNotes()
        }

        // ========================================================================================
        // the text in the status bar is initialized to 0
        statusBar.children.add(Label("0"))
        statusBar.padding = Insets(10.0)
        statusBar.spacing = 10.0

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

    // function to add the notes
    private fun addRandomNotes(importantFlg: Boolean){
        val titleStr = genParagraph().first
        val bodyStr = genParagraph().second
        // save current notes to our list for all lists
        noteCounter += 1
        val note = Note(noteCounter, titleStr, bodyStr, importantFlg)
        notesList.add(note)
        refreshUI()
    }

    private fun deleteNotes(){
        refreshUI()
    }

    private fun refreshUI(){
        // clear the current UI and show all the notes exist in the list
        flowPane.children.clear()
        flowPane.padding = Insets(10.0)
        flowPane.vgap = 10.0
        flowPane.hgap = 10.0
        for (aNote in notesList) {
            val notes = VBox()
            // set the background colors for the notes
            if (!aNote.isImportant){
                notes.style = "-fx-background-color:WHITE"
            }else{
                notes.style = "-fx-background-color:LIGHTYELLOW"
            }
            val title = Label(aNote.title)
            val body = Label(aNote.body).apply { this.isWrapText = true}
            // set the sizes as 150 by 200 unit rectangular areas
            notes.prefWidth = 150.0
            notes.prefHeight = 200.0
            // 10 unit space between the title and body.
            notes.spacing = 10.0
            // There’s a 10-unit margin inside the note rectangle
            notes.padding = Insets(10.0)
            notes.children.addAll(title, body)
            flowPane.children.add(notes)
        }
    }

    private fun clearNotes(){
        // TODO: When 1 or more notes are displayed, the “Clear” button is enabled.
        //  Pressing “Clear” removes all notes that are displayed
        //  (i.e. only those visible due to active filters, or all notes if no filters are active).
//        notesList.removeIf {it.isImportant==true} // remove all the important ones
        flowPane.children.clear()
    }

    // function to update the status bar
    // TODO: 全局变量 onclick 的时候handel
    private fun updateStatus(statusText: String){
        statusBar.children.clear()
        statusBar.children.add(Label(statusText))
    }
}

