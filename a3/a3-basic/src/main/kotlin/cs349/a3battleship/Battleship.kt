package cs349.a3battleship

import cs349.a3battleship.model.Game
import javafx.application.Application
import javafx.stage.Stage

class Battleship : Application() {
    override fun start(stage: Stage) {

        var game = Game(10, true)
        var computer = AI(game)
        // var player = ...
        game.startGame()

        // stage.scene = ...
        stage.show()
    }
}