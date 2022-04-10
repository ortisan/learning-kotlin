package ortisan

// https://kotlinlang.org/docs/functions.html
class Functions {
    companion object {
        fun defaultArguments(paramA: String = "Testing", paramB: String = "Default params"): String {
            return "${paramA} ${paramB}"
        }
        //https://kotlinlang.org/docs/functions.html#single-expression-functions
        fun singleExpressions(paramA: String, paramB: String): String = "${paramA} ${paramB}"
    }
}

fun main() {
    println(Functions.defaultArguments())
    println(Functions.defaultArguments(paramB = "With named params"))
    println(Functions.defaultArguments(paramB = "Without param order", paramA = "Testing"))
}