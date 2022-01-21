package algo.merge

enum class AddressType {
    BUSINESS,
    RESIDENTIAL,
    OTHER
}

data class Address(
    val id: String,
    val addressTypes: List<AddressType>,
    val line1: String,
    val completenessLevel: Int
) {

}


fun mergeData(
    newAddresses: List<Address>,
    addressesGoldenSource: List<Address>
): Triple<List<Address>?, List<Address>?, List<Address>?> {
    // normalize new addresses
    val normalizedNewAddresses =
        newAddresses.flatMap { address -> address.addressTypes.map { _type -> address.copy(addressTypes = listOf(_type)) } }
    val mapNewAddressByType = HashMap<AddressType, Address>();
    normalizedNewAddresses.forEach { it ->
        mapNewAddressByType[it.addressTypes[0]] = it
    }

    // normalize golden
    val normalizedGolden = addressesGoldenSource.flatMap { address ->
        address.addressTypes.map { _type ->
            address.copy(
                addressTypes = listOf(_type)
            )
        }
    }
    val mapGoldenAddressByType = HashMap<AddressType, Address>();
    val mapLine1HashByAddress = HashMap<Int, Address>();
    normalizedGolden.forEach { it ->
        mapGoldenAddressByType[it.addressTypes[0]] = it
        mapLine1HashByAddress[it.line1.hashCode()] = it
    }

    val newAddresses = ArrayList<Address>()
    val openDiffAddress = ArrayList<Address>()

    for (newAddress in normalizedNewAddresses) {
        val goldenAddressSameType = mapGoldenAddressByType[newAddress.addressTypes[0]]

        if (newAddress.addressTypes.contains(AddressType.OTHER)) {
            // if type other always add
            newAddresses.add(newAddress) // always add
        } else if (goldenAddressSameType != null) {
            // if already exists other address same type
            if (newAddress.completenessLevel >= goldenAddressSameType.completenessLevel) {
                // if completeness is higher
                newAddresses.add(newAddress)
            } else {
                // if completeness is lower
                newAddresses.add(newAddress.copy(addressTypes = listOf(AddressType.OTHER)))
                openDiffAddress.add(newAddress)
            }
        } else {
            newAddresses.add(newAddress)
        }
    }

    // Collect all addressTypes
    val uniqueTypesNewAddressType = newAddresses.flatMap { it.addressTypes }.filter { it != AddressType.OTHER }
    // Remove all types already set by newAddresses
    val updateAddressRemovedDuplicateTypes = normalizedGolden.map { address -> address.copy(addressTypes = address.addressTypes.filter { addressType -> !uniqueTypesNewAddressType.contains(addressType) })}

    // Regroup new addresses
    val newAddressesGrouped = newAddresses.groupBy { it.line1.hashCode() }.map { it ->
        it.value.reduce { accAddress, address ->
            accAddress.copy(
                addressTypes = (accAddress.addressTypes + address.addressTypes).toSet().toList()
            )
        }
    }

    val updatedAddressesGrouped = updateAddressRemovedDuplicateTypes.groupBy { it.line1.hashCode() }.map { it ->
        it.value.reduce { accAddress, address ->
            accAddress.copy(
                addressTypes = (accAddress.addressTypes + address.addressTypes).toSet().toList()
            )
        }
    }

    return Triple(newAddressesGrouped, updatedAddressesGrouped, openDiffAddress)
}

