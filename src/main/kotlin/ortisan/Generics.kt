package ortisan

//https://kotlinlang.org/docs/generics.html#declaration-site-variance
//https://kotlinlang.org/docs/generics.html#star-projections
class Generics {
    fun testingStar() {
        println("### Star: ")
        val personPhysicalToString = StringObjectsStar(PhysicalPerson("Marcelo", "44049401061"))
        println(personPhysicalToString)
        val personCorporatePersonToString = StringObjectsStar(CorporatePerson("Tentativafc SA", "12970979000100"))
        println(personCorporatePersonToString)
        val genericPhysicalPerson: StringObjectsStar<*> = personPhysicalToString;
        println(genericPhysicalPerson)
    }

    fun testingSiteVariance() {
        println("### Site variance: ")
        val personPhysicalToString = StringObjectsSiteVariance(PhysicalPerson("Marcelo", "44049401061"))
        println(personPhysicalToString)
        val personCorporatePersonToString = StringObjectsSiteVariance(CorporatePerson("Tentativafc SA", "12970979000100"))
        println(personCorporatePersonToString)
        val genericPhysicalPerson: StringObjectsSiteVariance<*> = personPhysicalToString;
        println(genericPhysicalPerson)
    }
}

// This works with star projection (https://kotlinlang.org/docs/generics.html#star-projections)
class StringObjectsStar<T>(val obj: T) {
    override fun toString(): String {
        return obj.toString()
    }
}
//This works with site variance (https://kotlinlang.org/docs/generics.html#declaration-site-variance)
class StringObjectsSiteVariance<out T>(val obj: T) {
    override fun toString(): String {
        return obj.toString()
    }
}

fun main(args: Array<String>) {
    Generics().testingStar()
    Generics().testingSiteVariance()
}
