package ortisan

import java.time.LocalDateTime
import kotlin.reflect.full.memberFunctions

abstract class FieldValidationData<T> {
    abstract var value: T
    abstract var validation: Validation

    fun clone(newValueParam: T? = null, newValidationParam: Validation? = null): FieldValidationData<T> {
        if (!this::class.isData) {
            throw Error("clone is only supported for data classes")
        }
        val copy = this::class.memberFunctions.first { it.name == "copy" }
        // check if null
        var newValue = newValueParam ?: this.value
        var newValidation = newValidationParam ?: this.validation
        return copy.call(this, newValue, newValidation) as FieldValidationData<T>
    }

    fun toFieldDataContext(): FieldContext<FieldValidationData<T>> {
        return FieldContext(this)
    }
}

data class FullName(
    override var value: UniqueDataValue<String>,
    override var validation: Validation
) : FieldValidationData<UniqueDataValue<String>>()

data class Age(
    override var value: UniqueDataValue<Int>,
    override var validation: Validation
) : FieldValidationData<UniqueDataValue<Int>>()


data class UniqueDataValue<out T>(
    val value: T
)

data class Validation(
    val date: LocalDateTime,
    val level: Long
)

data class FieldContext<out FieldValidation>(
    val newValue: FieldValidation
)

fun main() {

    val fullName = FullName(
        value = UniqueDataValue("Marcelo Ortiz de Santana"),
        validation = Validation(
            date = LocalDateTime.now(),
            level = 500
        )
    )

    val age = Age(
        value = UniqueDataValue(38),
        validation = Validation(
            date = LocalDateTime.now(),
            level = 500
        )
    )

    val fullNameCloned = fullName.clone(UniqueDataValue("Marcelo Ortiz"))
    println("fullNameCloned = ${fullNameCloned}")


    val fieldContext =
        listOf<FieldContext<FieldValidationData<*>>>(fullName.toFieldDataContext(), age.toFieldDataContext())

    fieldContext.forEach {
        println(it)
    }

    val nameAgain = fieldContext.get(0).newValue as FullName
    println("nameAgain = ${nameAgain}")
}

