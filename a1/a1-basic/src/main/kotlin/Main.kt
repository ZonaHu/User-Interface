import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.Stage

class Main : Application() {
    private val layout = BorderPane()

    override fun start(stage: Stage) {

        // set the title
        stage.title = "A1 Notes (z228hu)"
        stage.isResizable = true

        // create the tool bar
        val toolBar = HBox()
        // set button length
        val addButn = Button("Add")
        addButn.prefWidth = (100.0)
        val randomButn = Button("Random")
        randomButn.prefWidth = (100.0)
        val deleteButn= Button("Delete")
        deleteButn.prefWidth = (100.0)
        val clearButn= Button("Clear")
        clearButn.prefWidth = (100.0)
        val importantButn = ToggleButton("!")

        val scene = Scene((layout), 800.0, 600.0)
        toolBar.children.addAll(addButn, randomButn, deleteButn, clearButn, importantButn, TextField())
        toolBar.padding = Insets(10.0)
        toolBar.spacing = 10.0

        // scroll pane + flow pane in the center to display notes
        val flowPane = FlowPane()
        val notes = VBox()
        notes.prefWidth = 150.0
        notes.prefHeight = 200.0
        notes.spacing = 10.0


        // 全局变量是个数组（所有notes 数据结构 no ui）
        // 刷新 函数 把Flowpane children clear掉再根据数组创建
        // background ， margin, spacing
        // 弹出框

        // press the random button
        // adds a new note with a random title,
        // random body, and random chance of it
        // being marked as “important”.
        notes.children.add(Label(genParagraph().first))
        notes.children.add(Label(genParagraph().second).apply { this.isWrapText = true})

        flowPane.children.add(notes)

        val scrollPane = ScrollPane(flowPane)
        // bottom status bar
        val bottomPart = HBox()
        // 全局变量 unclick 的时候handel
        bottomPart.children.add(Label("Hi"))

        // set up the layout and the scene
        layout.top = toolBar
        layout.center = scrollPane
        layout.bottom = bottomPart

//        val stackPane = StackPane(layout)
        // 弹出框 VBox 是第二个元素在stack Pane

        // set. onclick for the buttons


        // show the scene
        stage.scene = scene
        stage.minWidth = 400.0
        stage.minHeight = 400.0
        stage.show()
    }
}