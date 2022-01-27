import java.util.*
import kotlin.random.Random

// there’s about a 1 in 5 chance that the note is flagged as important.
fun genParagraph( ): Pair<String, String> {
    // the random words are taken from https://www.lipsum.com/
    val strDictionary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus dictum sem lobortis nisi eleifend, vel imperdiet nunc commodo. Donec risus eros, pulvinar nec porttitor quis, mollis eu magna. Sed urna mauris, posuere at diam id, placerat posuere ante. Aliquam venenatis, magna at tincidunt tempus, metus purus vestibulum neque, ut consectetur arcu nulla et purus. Donec ultricies massa nec nunc pellentesque consequat. Donec gravida laoreet sapien, nec cursus augue interdum quis. Curabitur sollicitudin purus vel condimentum congue. Integer sed fringilla urna. Duis sit amet viverra velit. Aenean est leo, commodo vitae mattis in, finibus nec nibh. Duis mattis vulputate sapien vitae bibendum. Nullam nulla nisl, dictum vel pretium vel, blandit vel diam. Fusce eu eros consectetur, semper tellus sit amet, cursus ligula. Cras sapien nisi, molestie in nisi vitae, condimentum malesuada est Quisque vulputate vitae dui a pellentesque. Praesent arcu magna, egestas vitae tellus nec, euismod ullamcorper justo. Vestibulum porta sodales neque, eget feugiat dui laoreet nec. In nunc nibh, aliquet id ligula quis, semper maximus nibh. Aliquam sit amet elementum lacus. Sed et dui placerat, congue neque eu, efficitur leo. Sed dui lorem, aliquam ac nunc scelerisque, efficitur auctor libero. Pellentesque vulputate lacus nec interdum hendrerit. Phasellus id dui at nisi gravida condimentum. Donec semper et justo quis luctus. Donec condimentum efficitur mi et accumsan. Duis finibus, justo ac porttitor molestie, nisl lectus elementum augue, et eleifend enim sem vel ante. Nulla porta, justo at pretium ultricies, diam felis sagittis quam, sed eleifend sem nisl non odio".replace(",","a").replace(".", "b").lowercase().split(" ")
    var title = ""
    val titleNum = Random.nextInt(1,4)
    var body = ""
    val bodyNum = Random.nextInt(2,6)
    for (i in 1..titleNum) {
        title += strDictionary.random()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        if (i != titleNum){
            title += " "
        }
    }
    for (j in 1..bodyNum){
        var bodySent = ""
        val bodySentNum = Random.nextInt(3,11)
        for (i in 1..bodySentNum) {
            var tmp = strDictionary.random()
            if (i == 1){
                tmp = tmp.capitalize()
            }else if (i == bodySentNum){
                tmp += "."
            }
            bodySent += tmp
            bodySent += " "
        }
        body += bodySent
    }
    return Pair(title, body) ;
}
