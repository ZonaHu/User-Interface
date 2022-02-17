import javafx.scene.paint.Color

fun createColorOption(color: String): String {
    return when (color) {
        "Rainbow" -> Color.GRAY.toString()
        "Light Sky Blue" -> Color.LIGHTSKYBLUE.toString()
        "Chocolate" -> Color.CHOCOLATE.toString()
        "Light Pink" -> Color.LIGHTPINK.toString()
        "Light Salmon" -> Color.LIGHTSALMON.toString()
        "Lime Green" -> Color.LIMEGREEN.toString()
        else -> Color.BLACK.toString()
    }
}