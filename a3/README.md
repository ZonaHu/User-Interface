# A3 Battleship
Zuomiao Hu (z228hu 20815304)
 
## Setup
* macOS 12.2.1
* IntelliJ IDEA 2021.3.1 (Ultimate Edition)
* kotlin.jvm 1.6.10
* javafxplugin 0.0.10
* Java SDK 16.0.2 (Amazon Corretto)
* JavaFX 17.0.1

## Enhancement 
* Added colors to ships with (Color.CHOCOLATE, Color.PINK, Color.ORANGE,  Color.LIMEGREEN, Color.BLUE)
  - set player's ships that are sunk to dark gray to show the state more clearly
* Added an area to show the opponent's available fleets:
  - Transparent rectangles means ships that are still available
  - Rectangles in dark gray means the ships are sunk
  - Helpful for the users to make guesses
* Enhanced game controls, modified layouts and added a button to attack randomly for the human player