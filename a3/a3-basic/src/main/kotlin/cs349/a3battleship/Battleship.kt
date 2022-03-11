package cs349.a3battleship

import cs349.a3battleship.model.Game
import cs349.a3battleship.ui.Movable
import cs349.a3battleship.ui.OpponentBoardView
import cs349.a3battleship.ui.PlayerBoardView
import cs349.a3battleship.ui.ShipAreaView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class Battleship : Application() {
    override fun start(stage: Stage) {
        // ============ set the window name =======================
        stage.title = "A3 Battleship (z228hu)"
        // make the stage not resizable
        stage.isResizable = false

        var game = Game(10, true)
        var computer = AI(game)
        // var player = ...
        game.startGame()

        val layout = BorderPane()
        // moving node manager
        val mover = Movable(game, layout)
        layout.left = PlayerBoardView(game)
        layout.right = OpponentBoardView(game)
        layout.center = ShipAreaView(game, mover)


        // =========== create the scene with initialized size of 875 by 375 units ===============================
        // Add layout to a scene (and the scene to the stage)， initial size 875 by 375
        val scene = Scene(layout, 875.0, 375.0)

        // ============================ show the scene===========================================================
        stage.scene = scene
        stage.show()
    }
}