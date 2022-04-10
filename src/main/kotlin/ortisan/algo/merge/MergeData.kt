package ortisan.algo.merge

import ortisan.util.DigestUtil

enum class AddressType {
    BUSINESS,
    RESIDENTIAL,
    OTHER
}

data class Address(
    val id: String? = null,
    val line1: String,
    val addressTypes: List<AddressType>,
    val completenessLevel: Int
) {
    fun getHashValue(): String {
        return DigestUtil.getMD5FromString("line1: ${this.line1}");
    }
}

fun mergeData(
    newAddresses: List<Address>,
    addressesGoldenSource: List<Address>
): Triple<List<Address>?, List<Address>?, List<Address>?> {

    // Normalize new addresses
    // One address with 2 types, will be flat map. One address with one type.
    val normalizedNewAddresses =
        newAddresses.flatMap { address -> address.addressTypes.map { _type -> address.copy(addressTypes = listOf(_type)) } }

    // Create hashmap. Address(value) by type(key)
    val mapNewAddressByType = HashMap<AddressType, Address>();
    normalizedNewAddresses.forEach { it ->
        mapNewAddressByType[it.addressTypes.first()] = it
    }

    // Normalize golden addresses
    // One address with 2 types, will be flat map, resulting in One address for One type.
    val normalizedGolden = addressesGoldenSource.flatMap { address ->
        address.addressTypes.map { _type ->
            address.copy(
                addressTypes = listOf(_type)
            )
        }
    }

    // Create hashmap. Address(value) by Type(key)
    val mapGoldenAddressByType = HashMap<AddressType, Address>();
    val mapAddressByHashValue = HashMap<String, Address>();
    normalizedGolden.forEach { it ->
        mapGoldenAddressByType[it.addressTypes.first()] = it
        mapAddressByHashValue[it.getHashValue()] = it
    }

    val newAddresses = ArrayList<Address>()
    val openDiffAddress = ArrayList<Address>()

    normalizedNewAddresses.forEach { newAddress ->
        val goldenAddressSameType = mapGoldenAddressByType[newAddress.addressTypes.first()]

        if (newAddress.addressTypes.first() == AddressType.OTHER) {
            // if type is other, always add
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
    val addressesToUpdateDuplicateTypesRemoved =
        if (uniqueTypesNewAddressType.isNotEmpty()) {
            normalizedGolden.filter {
                // First because the normalization
                uniqueTypesNewAddressType.contains(it.addressTypes.first())
            }.map { goldenAddress ->
                // Remove from golden all unique types (Residential and Business). If empty update with Other.
                val adressesTypes = goldenAddress.addressTypes.filter { addressType ->
                    !uniqueTypesNewAddressType.contains(addressType)
                }.ifEmpty { listOf(AddressType.OTHER) }
                goldenAddress.copy(addressTypes = adressesTypes)
            }
        } else emptyList()

    // Regroup addresses to insert
    // Remove denormalisation 1 address type -> n.
    val newAddressesGrouped = newAddresses.groupBy { it.line1.hashCode() }.map { it ->
        it.value.reduce { accAddress, address ->
            accAddress.copy(
                addressTypes = (accAddress.addressTypes + address.addressTypes).toSet().toList()
            )
        }
    }

    // Regroup addresses to update
    // Remove denormalisation 1 address type -> n.
    val addressesToUpdateGrouped = addressesToUpdateDuplicateTypesRemoved.groupBy { it.getHashValue() }.map { it ->
        it.value.reduce { accAddress, address ->
            accAddress.copy(
                addressTypes = (accAddress.addressTypes + address.addressTypes).toSet().toList()
            )
        }
    }

    // Triple contains:
    // Left. New addresses - Addresses that need be inserted.
    // Middle. Updated addresses - Addresses that need to be updated.
    // Right. Diff Addresses - Addresses that will open new version (Diff).
    return Triple(newAddressesGrouped, addressesToUpdateGrouped, openDiffAddress)
}

