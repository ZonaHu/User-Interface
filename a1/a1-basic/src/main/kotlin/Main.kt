import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
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

    // set up the buttons
    private val addButn = Button("Add")
    private val randomButn = Button("Random")
    private val deleteButn= Button("Delete")
    private val clearButn= Button("Clear")
    private val importantButn = ToggleButton("!")
    private val inputBox = TextField()

    // list 存所有notes， 通过size()
    private var notesList = mutableListOf<Note> ()
    // counter for the number of notes existed
    private var noteCounter = 0
    // an id that denotes the current note's id that is clicked
    private var clickedId = 0

    override fun start(stage: Stage) {
        // ============ set the title =======================
        stage.title = "A1 Notes (z228hu)"
        // make the stage resizable
        stage.isResizable = true

        //============ add the buttons and then set button width to 100 =============================
        addButn.prefWidth = (100.0)
        randomButn.prefWidth = (100.0)
        // delete is only valid if there is some selected
        deleteButn.prefWidth = (100.0)
        deleteButn.isDisable = true
        // clear is only valid if there is at least one note
        clearButn.prefWidth = (100.0)
        clearButn.isDisable = true
        // TODO: text field can be wider
        inputBox.prefWidth = (200.0)

        toolBar.children.addAll(addButn, randomButn, deleteButn, clearButn, importantButn, inputBox)
        // set spacing and padding
        toolBar.padding = Insets(10.0)
        toolBar.spacing = 10.0

        // ================ set up the centered scroll pane ======================================
        // hide the horizontal scroll bar for smaller size windows
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER;
        // make sure the scroll pane is fit to width
        scrollPane.isFitToWidth = true

        // ============== handle the actions with the buttons =====================================
        // implement the add function
        addButn.setOnAction {
            addNotes(importantButn.isSelected, inputBox.text)
        }

        // function to handle click on Random button
        randomButn.setOnAction {
            // there’s about a 1 in 5 chance that the note is flagged as important
            val imp = (Random.nextInt(5) == 0)
            addRandomNotes(imp, importantButn.isSelected, inputBox.text)
        }

        // function to delete a note
        deleteButn.setOnAction {
            deleteNotes(importantButn.isSelected, clickedId, inputBox.text)
        }

        // implement the clear all notes
        clearButn.setOnAction {
            // clear all notes
            clearNotes(importantButn.isSelected, inputBox.text)
        }

        importantButn.setOnAction{
            refreshDisplay(importantButn.isSelected, inputBox.text)
        }

        // immediate text search is case-insensitive according to the description
        inputBox.textProperty().addListener{ _, _, inputText -> refreshDisplay(importantButn.isSelected, inputText.lowercase())}

        // ============== handle the click actions with the notes =====================================


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

    private fun addNotes(selected: Boolean, text: String) {

        refreshDisplay(selected, text)
    }

    // function to add the notes
    private fun addRandomNotes(importantFlg: Boolean, selected: Boolean, text: String){
        val titleStr = genParagraph().first
        val bodyStr = genParagraph().second
        // save current notes to our list for all lists
        noteCounter += 1
        val note = Note(noteCounter, titleStr, bodyStr, importantFlg)
        notesList.add(note)
        refreshDisplay(selected, text)
    }

    private fun deleteNotes(selected: Boolean, targetId: Int, text: String) {
        notesList.removeIf{it.id == targetId}
        refreshDisplay(selected, text)
    }

    private fun clearNotes(selected: Boolean, text: String) {
        //  the “Clear” button is enabled when 1 or more notes are displayed
        //  removes all notes that are displayed (might be under the text filter) according to the filter
        if (text != ""){
            notesList.removeIf {it.title.lowercase().contains(text) or it.body.lowercase().contains(text) }
        }
        if (selected){ // under important filter, only remove all the important notes
            if (text == ""){
                notesList.removeIf { it.isImportant }
            }
        }else{ //  no filter for importance on
            if (text == ""){
                // remove everything from the list
                notesList.clear()
            }
        }
        refreshDisplay(selected, text)
    }

    private fun setUpFlowPane(){
        // clear the current UI and show all the notes exist in the list
        flowPane.children.clear()
        flowPane.padding = Insets(10.0)
        flowPane.vgap = 10.0
        flowPane.hgap = 10.0
    }

    private fun refreshDisplay(selected: Boolean, text: String){
        setUpFlowPane()
        var numImportant = 0
        for (aNote in notesList) {
            if (text!=""){ // text filter is on
                if (!aNote.title.lowercase().contains(text) and !aNote.body.lowercase().contains(text)){
                    continue
                }
            }
            if (selected){
                if (!aNote.isImportant){
                    continue
                }
                numImportant += 1
            }
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
            title.alignment = Pos.TOP_LEFT
            body.alignment = Pos.TOP_LEFT
            notes.children.addAll(title, body)
            //  notes can be selected with a single click
            notes.setOnMouseClicked {
                // reset the borders for all notes
                for (tmp in flowPane.children){
                    val oneNote = tmp as VBox
                    oneNote.border = null
                }
                // TODO: Filtering does not affect the selected note.
                if (( clickedId == aNote.id)){
                    // unselect only if the note is already selected in CURRENT filter
                    clickedId = 0
                    deleteButn.isDisable = true // reset the delete button to be disabled
                }else{
                    // select and add a border
                    clickedId = aNote.id
                    deleteButn.isDisable = false
                    notes.border = Border(
                        BorderStroke( // from paint
                            Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(1.0)
                        ))
                }
                //TODO: update the status bar
            }
            deleteButn.isDisable = true // if no note is selected, delete button is false
            flowPane.children.add(notes)
        }
        // clear is only valid if there is at least one note being displayed
        if (!selected) {clearButn.isDisable = notesList.size < 1} // if important filter is not on
        // if important filter is on:
        else {
            //the button is not valid if there is no important note
            clearButn.isDisable = (numImportant == 0)
        }
    }

    // function to update the status bar
    // TODO: 全局变量 onclick 的时候handel
    private fun updateStatus(statusText: String){
        statusBar.children.clear()
        statusBar.children.add(Label(statusText))
    }
}

