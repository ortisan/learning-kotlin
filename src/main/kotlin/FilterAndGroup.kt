import java.time.LocalDateTime

data class Address(
    val id: String?,
    val line1: String?,
    val listAuditMetadata: List<AddressAuditMetadata>? = emptyList()
)

fun List<Address>.groupById(): Map<String, List<Address>> {
    return this.groupBy { it.id!! }
}

fun List<Address>.filterById(id: String?): List<Address> {
    return this.filter { it.id == id }
}

fun List<Address>.mergeAuditData(auditsMetadata: List<AddressAuditMetadata>?): List<Address> {
    val groupByAddressId = auditsMetadata?.groupByAddressId()
    return this.map {
        val auditsOfThisAddress = groupByAddressId?.get(it.id!!) ?: emptyList()
        if (auditsOfThisAddress.isEmpty()) {
            it
        } else {
            it.copy(listAuditMetadata = auditsOfThisAddress)
        }
    }
}

data class AddressAuditMetadata(
    val id: String?,
    val user: String?,
    val addressId: String?,
    val updatedAt: LocalDateTime? = LocalDateTime.now()
)

fun List<AddressAuditMetadata>.filterByAddressId(addressId: String?): List<AddressAuditMetadata> {
    return this.filter { it.addressId == addressId }
}

fun List<AddressAuditMetadata>.groupByAddressId(): Map<String, List<AddressAuditMetadata>> {
    return this.groupBy { it.addressId!! }
}



