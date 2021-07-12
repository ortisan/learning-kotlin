import kotlin.reflect.full.memberFunctions

abstract class Nameable {
    abstract fun getName(): String

    fun clone(newName: String): Any? {
        val copy = this::class.memberFunctions.first { it.name == "copy" }
        return copy.call(this, newName, 21)
    }
}

data class PersonCopy(private val name: String, val age: Int?) : Nameable() {
    override fun getName(): String {
        return this.name
    }
}


fun main(args: Array<String>) {
    val person = PersonCopy("Marcelo", 10)
    val newPerson = person.clone("Joao da Silva")

    println("newPerson = ${newPerson}")
}
