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
    // basic idea: I use an array to store all the notes, and update each time when buttons are clicked

    // ======================================== initialization =========================================
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

    // ================================= set up the buttons============================================
    private val addButn = Button("Add")
    private val randomButn = Button("Random")
    private val deleteButn = Button("Delete")
    private val clearButn = Button("Clear")
    private val importantButn = ToggleButton("!")
    private val inputBox = TextField()

    // ==================== initialize the counters, status strings, flags and notes list==================
    // list contains all the notes currently available
    private var notesList = mutableListOf<Note>()

    // counter for the number of notes existed
    private var noteCounter = 0

    // an id that denotes the current note's id that is clicked
    private var clickedId = -1

    //  an id that denotes the last note's id that is edited
    private var editedId = 0

    // displayed number of notes
    private var numDisplayed = 0
    private var isCleared = false
    private var selectedIsCleared = false
    private var clearedNum = 0
    private var targetId = 0

    // the string denotes current status of the status bar
    private var curStatus = ""

    // determine the text that status bar should display
    private var statusText = ""
    private var statusText2 = ""

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
        // text field can be wider
        inputBox.prefWidth = (200.0)

        toolBar.children.addAll(addButn, randomButn, deleteButn, clearButn, importantButn, inputBox)
        // set spacing and padding
        toolBar.padding = Insets(10.0)
        toolBar.spacing = 10.0

        // ================ set up the centered scroll pane ======================================
        // hide the horizontal scroll bar for smaller size windows
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
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
            deleteNotes(importantButn.isSelected, inputBox.text)
        }

        // implement the clear all notes
        clearButn.setOnAction {
            // clear all notes
            clearNotes(importantButn.isSelected, inputBox.text)
        }

        importantButn.setOnAction {
            refreshDisplay(importantButn.isSelected, inputBox.text)
        }

        // immediate text search is case-insensitive according to the description
        inputBox.textProperty()
            .addListener { _, _, inputText -> refreshDisplay(importantButn.isSelected, inputText.lowercase()) }

        // ===============================set up the default status bar =========================================
        // the text in the status bar is initialized to 0
        statusBar.children.add(Label("0"))
        statusBar.padding = Insets(10.0)
        statusBar.spacing = 30.0

        // ===================================set up the layout and the scene====================================
        layout.top = toolBar
        layout.center = scrollPane
        layout.bottom = statusBar

        // =========== create the scene with initialized size of 800 by 600 units ===============================
        val scene = Scene((stackPane), 800.0, 600.0)

        // ============================ show the scene===========================================================
        stage.scene = scene
        stage.minWidth = 400.0
        stage.minHeight = 400.0
        stage.show()
    }

    private fun saveFunc(messg: String, title: String, body: String, importantFlg: Boolean) {
        // if it's an add:
        if (messg == "Add New Note") {
            noteCounter += 1
            val note = Note(noteCounter, title, body, importantFlg)
            notesList.add(note)
            curStatus = "add"
        } else { // it's edit, find the editId and change its title and content
            notesList.single { it.id == editedId }.body = body
            notesList.single { it.id == editedId }.title = title
            notesList.single { it.id == editedId }.isImportant = importantFlg
            curStatus = "edited"
        }
    }

    private fun createNotes(
        importantFlg: Boolean,
        messg: String,
        text: String,
        id: Int,
        title: String,
        body: String,
        important: Boolean
    ) {
        // initialize layouts for the pop-up  form and dark background
        val addRectangle = Region()
        val popUp = GridPane()
        // set the sizes, padding, and colors
        addRectangle.style = "-fx-background-color:GRAY"
        addRectangle.opacity = 0.5
        addRectangle.isVisible = true
        popUp.setMaxSize(400.0, 300.0)
        popUp.style = "-fx-background-color:LIGHTGRAY"
        popUp.vgap = 10.0
        popUp.hgap = 10.0
        popUp.isDisable = false
        popUp.isVisible = true
        // initialize the elements that need to show in the pop-up form
        val paneTitle = Label(messg)
        val noteTitle = Label("Title")
        val noteBody = Label("Body")
        val importantText = Label("Important")
        val checkBox = CheckBox()
        val saveButn = Button("Save")
        val cancelButn = Button("Cancel")
        saveButn.prefWidth = (100.0)
        cancelButn.prefWidth = (100.0)
        val titleTextField = TextField()
        val bodyTextField = TextArea()
        bodyTextField.isWrapText = true
        if (messg != "Add New Note") {
            editedId = id;
            titleTextField.text = title
            bodyTextField.text = body
            checkBox.isSelected = important
        }
        // Grid Pane layout
        popUp.padding = Insets(10.0)
        popUp.add(paneTitle, 0, 0, 6, 1)
        popUp.add(noteTitle, 0, 2, 1, 1)
        popUp.add(titleTextField, 1, 2, 20, 1)
        popUp.add(noteBody, 0, 3, 1, 1)
        popUp.add(bodyTextField, 1, 3, 20, 1)
        popUp.add(checkBox, 1, 4, 3, 1)
        popUp.add(importantText, 3, 4, 5, 1)
        popUp.add(saveButn, 8, 6, 6, 3)
        popUp.add(cancelButn, 15, 6, 6, 3)
        // add to our stack pane
        stackPane.children.addAll(addRectangle, popUp)
        // function to delete a note
        saveButn.setOnAction {
            // in save, information are saved and noteCounter += 1
            val curTitle = titleTextField.text
            val curBody = bodyTextField.text
            val imp = checkBox.isSelected
            saveFunc(messg, curTitle, curBody, imp)
            stackPane.children.removeAll(addRectangle, popUp)
            refreshDisplay(importantFlg, text) // reprint the UI
        }
        // function to delete a note
        cancelButn.setOnAction {
            // make no change
            stackPane.children.removeAll(addRectangle, popUp)
        }
    }

    private fun addNotes(selected: Boolean, text: String) {
        // function to add new notes
        createNotes(selected, "Add New Note", text, -1, "", "", false)
    }

    private fun editNotes(selected: Boolean, text: String, aNote: Note) {
        // function to edit the current notes
        val message = "Edit Note " + aNote.id.toString()
        createNotes(selected, message, text, aNote.id, aNote.title, aNote.body, aNote.isImportant)
    }

    private fun addRandomNotes(importantFlg: Boolean, selected: Boolean, text: String) {
        // function to add the random notes
        val titleStr = RandomGen().getTitle()
        val bodyStr = RandomGen().getBody()
        // save current notes to our list for all lists
        noteCounter += 1
        val note = Note(noteCounter, titleStr, bodyStr, importantFlg)
        notesList.add(note)
        curStatus = "add"
        refreshDisplay(selected, text)
    }

    private fun deleteNotes(selected: Boolean, text: String) {
        // function to delete notes
        notesList.removeIf { it.id == clickedId }
        curStatus = "delete"
        targetId = clickedId
        clickedId = 0
        refreshDisplay(selected, text)
    }

    private fun clearNotes(selected: Boolean, text: String) {
        //  the “Clear” button is enabled when 1 or more notes are displayed
        //  removes all notes that are displayed (might be under the text filter) according to the filter
        if (text != "") {
            notesList.removeIf { it.title.lowercase().contains(text) or it.body.lowercase().contains(text) }
        }
        if (selected) { // under important filter, only remove all the important notes
            if (text == "") {
                notesList.removeIf { it.isImportant }
            }
        } else { //  no filter for importance on
            if (text == "") {
                // remove everything from the list
                notesList.clear()
            }
        }
        // create a list to store note if it has the same id as the selected one
        val result = notesList.map { it.id == clickedId }
        if (result.isEmpty()) {
            selectedIsCleared = true
            clickedId = 0
        }
        curStatus = "clear"
        // update the UI
        refreshDisplay(selected, text)
    }

    private fun setUpFlowPane() {
        // clear the current UI and show all the notes exist in the list
        flowPane.children.clear()
        flowPane.padding = Insets(10.0)
        flowPane.vgap = 10.0
        flowPane.hgap = 10.0
    }

    private fun refreshDisplay(impFilterSelected: Boolean, text: String) {
        setUpFlowPane()
        var numImportant = 0
        var isFiltered = true
        if (curStatus == "clear" && !isCleared) { // the number of cleared notes doesn't change when filter changes
            clearedNum = numDisplayed // make sure it's not 0 from filter
            isCleared = true
        }
        if (curStatus != "clear") {
            isCleared = false
        }
        numDisplayed = 0
        if (!impFilterSelected and (text == "")) {
            numDisplayed = notesList.size
            isFiltered = false
        }
        for (aNote in notesList) {
            if (text != "") { // text filter is on
                if (!aNote.title.lowercase().contains(text) and !aNote.body.lowercase().contains(text)) {
                    continue
                }
                isFiltered = true
            }
            if (impFilterSelected) {
                if (!aNote.isImportant) {
                    continue
                }
                numImportant += 1
                isFiltered = true
            }
            val notes = VBox()
            // set the background colors for the notes
            if (!aNote.isImportant) {
                notes.style = "-fx-background-color:WHITE"
            } else {
                notes.style = "-fx-background-color:LIGHTYELLOW"
            }
            val title = Label(aNote.title)
            val body = Label(aNote.body).apply { this.isWrapText = true }
            // Filtering does not affect the selected note.
            if (aNote.id == clickedId) {
                deleteButn.isDisable = false
                notes.border = Border(
                    BorderStroke( // from paint
                        Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(1.0)
                    )
                )
            }else{
                // add an invisible border to prevent border slight changes when switch to blue border
                notes.border = Border(
                    BorderStroke( // from paint
                        Color.gray(0.0, 0.0), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(1.0)
                    )
                )
            }
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
                if (it.clickCount == 1) {
                    // reset the borders for all notes
                    for (tmp in flowPane.children) {
                        val oneNote = tmp as VBox
                        oneNote.border = Border(
                            BorderStroke( // from paint
                                Color.gray(0.0, 0.0), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(1.0)
                            )
                        )
                    }
                    if ((clickedId == aNote.id)) {
                        // unselect only if the note is already selected in CURRENT filter
                        clickedId = 0
                        curStatus = "notSelected"
                        deleteButn.isDisable = true // reset the delete button to be disabled
                    } else {
                        // select and add a border
                        clickedId = aNote.id
                        selectedIsCleared = false
                        deleteButn.isDisable = false
                        curStatus = "selected"
                        notes.border = Border(
                            BorderStroke( // from paint
                                Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths(1.0)
                            )
                        )
                    }
                    // pass in the number of notes we displayed
                    updateStatusBar(numDisplayed, isFiltered)
                } else {// double click
                    editNotes(impFilterSelected, text, aNote)
                }
            }
            if (isFiltered) {
                numDisplayed++ // increment the displayed counter
            }
            flowPane.children.add(notes)
        }
        // if size = 0 deleteButn must be disabled
        if ((notesList.size == 0)) {
            clickedId = 0
        }
        // if no note is selected or the selected one is deleted, delete button is disabled
        if ((curStatus == "delete") or (clickedId == 0)) {
            deleteButn.isDisable = true
        }

        // clear is only valid if there is at least one note being displayed
        if (!impFilterSelected) {
            clearButn.isDisable = notesList.size < 1
        } // if important filter is not on
        // if important filter is on:
        else { //the button is not valid if important filter is on and there is no important note
            clearButn.isDisable = (numImportant == 0)
        }
        // pass in the number of notes we displayed
        updateStatusBar(numDisplayed, isFiltered)
    }

    private fun updateStatusBar(numDisplayed: Int, isFiltered: Boolean) {
        // the first portion of the status bar
        statusText = if ((curStatus == "selected") and (!isFiltered)) {
            "#${clickedId - 1} | " + notesList.size.toString()
        } else if (isFiltered) {
            if (curStatus == "selected") {
                "#${clickedId - 1} | " + "$numDisplayed (of " + notesList.size.toString() + ")"
            } else {
                "$numDisplayed (of " + notesList.size.toString() + ")"
            }
        } else {
            notesList.size.toString()
        }

        // determine the second portion of the text that status bar should display
        when (curStatus) {
            "add" -> {
                statusText2 = "Added Note #"
                val num = noteCounter - 1
                statusText2 += num.toString()
            }
            "delete" -> {
                statusText2 = "Deleted Note #${targetId - 1}"
            }
            "clear" -> {
                // cleared number displayed notes
                statusText2 = "Cleared $clearedNum notes"
            }
            "edited" -> {
                statusText2 = "Edited Note #${editedId - 1}"
            }
        }
        updateStatus(statusText, statusText2)
    }

    // function to update the status bar
    private fun updateStatus(statusText: String, statusText2: String) {
        statusBar.children.clear()
        statusBar.children.addAll(Label(statusText), Label(statusText2))
    }
}