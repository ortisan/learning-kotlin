package ortisan

import java.time.LocalDateTime

data class AddressFilterGroup(
    val id: String?,
    val line1: String?,
    val listAuditMetadata: List<AddressAuditMetadata>? = emptyList()
)

fun List<AddressFilterGroup>.groupById(): Map<String, List<AddressFilterGroup>> {
    return this.groupBy { it.id!! }
}

fun List<AddressFilterGroup>.filterById(id: String?): List<AddressFilterGroup> {
    return this.filter { it.id == id }
}

fun List<AddressFilterGroup>.mergeAuditData(auditsMetadata: List<AddressAuditMetadata>?): List<AddressFilterGroup> {
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





