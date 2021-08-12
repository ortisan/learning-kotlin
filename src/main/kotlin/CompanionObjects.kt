//Like scala: https://docs.scala-lang.org/overviews/scala-book/companion-objects.html
//https://kotlinlang.org/docs/object-declarations.html#companion-objects

/**
 * Used to static declarations
 */
class CompanionObjects {
    companion object {
        const val HELLO = "HELLO"
        const val TITLE = "COMPANION OBJECTS"

        fun showTitle(): String {
            return "${HELLO} ${TITLE}"
        }
    }
}

fun main() {
    println(CompanionObjects.HELLO)
    println(CompanionObjects.TITLE)
    println(CompanionObjects.showTitle())
}