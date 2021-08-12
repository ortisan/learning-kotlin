// Copy of destruction on javascript (https://developer.mozilla.org/pt-BR/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment)
class DestructionDeclarations {

    fun destructExamples() {
        var arr = arrayOf("Hello", "World", "Learning", "Kotlin")
        var (hello, world) = arr
        println("${hello} ${world}")
    }
}

fun main(args: Array<String>) {
    DestructionDeclarations().destructExamples()
}