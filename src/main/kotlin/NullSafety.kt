//https://kotlinlang.org/docs/null-safety.html#nullable-types-and-non-null-types
class NullSafety {

    fun setNull() {
        var a: String = "abc" // Regular initialization means non-null by default
        // We cannot set null into variable.
        // a = null // compilation error

        // Instead we can use nullable string with "String?"
        var b: String? = "abc" // can be set null
        b = null // ok
        println(b)
    }

    fun checkNull() {
        var b: String? = null // can be set null
        println(b)
        // We cannot execute method because it can be null.
        // println(b.length)

        // There is an options to fix
        // 1. Check for null condition (https://kotlinlang.org/docs/null-safety.html#checking-for-null-in-conditions)
        println(if (b != null) b.length else -1)
        // 2. Use safe calls (https://kotlinlang.org/docs/null-safety.html#safe-calls)
        println(b?.length)
        // 3. Elvis operator (https://kotlinlang.org/docs/null-safety.html#elvis-operator).
        // Like 2, but with default value if is empty (include "?:" and default value)
        println(b?.length ?: -1)
        // 4. If developers want to treat NPE, use "!!" operator (https://kotlinlang.org/docs/null-safety.html#the-operator)
        var length_b = 0
        try {
            length_b = b!!.length
        } catch (exc: NullPointerException) {
            length_b = -1
        }
        println(length_b)

    }
}


fun main(args: Array<String>) {

    NullSafety().setNull()
    NullSafety().checkNull()

}