//https://kotlinlang.org/docs/inheritance.html
class Inheritence {
    fun createPersons() {
        val physical: Person = PhysicalPerson("Marcelo", "44049401061")
        val corporatePerson: Person = CorporatePerson("Tentativafc SA", "12970979000100")
        println(physical)
        println(corporatePerson)
    }
}

open class Person(val name: String) {
    override fun toString(): String {
        return "name: " + name
    }
}

class PhysicalPerson(name: String, val cpf: String) : Person(name) {
    override fun toString(): String {
        return super.toString() + ", cpf: " + cpf
    }
}

class CorporatePerson(name: String, val cnpj: String) : Person(name) {
    override fun toString(): String {
        return super.toString() + ", cnpj: " + cnpj
    }
}

fun main(args: Array<String>) {
    Inheritence().createPersons()
}


