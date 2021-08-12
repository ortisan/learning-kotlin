package algo.merge

enum class AddressType {
    BUSINESS,
    RESIDENTIAL,
    OTHER
}

data class Address(val id: String,
                   val addressType: AddressType,
                   val line1: String? = null,
                   val zipcode: String? = null)


data class FieldAddress(val newValue: Address,
                        val actualValue: Address)


fun mergeData(addressesGoldenSource: List<FieldAddress>, addressesDiffs: List<FieldAddress>): List<Address> {
    val diffByIdNew = addressesDiffs.groupBy { it.newValue.id }
    val diffByIdActual = addressesDiffs.groupBy { it.actualValue.id }

    return addressesGoldenSource
            .map {
                val id = it.newValue.id
                if (!diffByIdActual.get(it.newValue.id).isNullOrEmpty()) {
                    null
                } else {
                    val diffsOpen = diffByIdNew.get(id)
                    if (diffsOpen.isNullOrEmpty()) {
                        it
                    } else {
                        val firstDiff = diffsOpen.get(0)
                        it.copy(newValue = it.newValue.copy(addressType = firstDiff.newValue.addressType))
                    }
                }
            }
            .filter { it != null }
            .map { it!!.newValue }

}

