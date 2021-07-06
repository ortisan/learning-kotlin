import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class FilterAndGroupTest {

    private fun getAddresses(): List<Address> {
        val address = Address(id = "1", line1 = "Street 1")
        val address2 = Address(id = "2", line1 = "Street 1")
        val address3 = Address(id = "3", line1 = "Street 1")

        return listOf(address, address2, address3)
    }

    private fun getAddressesAudits(): List<AddressAuditMetadata> {
        val addressAuditMetadata = AddressAuditMetadata(
            id = "10",
            user = "marcelo_user",
            addressId = "1"
        )

        val addressAuditMetadata2 = AddressAuditMetadata(
            id = "20",
            user = "marcelo_user",
            addressId = "2"
        )

        return listOf(addressAuditMetadata, addressAuditMetadata2)
    }

    @Test
    fun testFilters() {

        val addresses = getAddresses()
        val addressesFiltered = addresses.filterById(id = addresses.first().id)

        assertEquals(1, addressesFiltered.size)


        val audits = getAddressesAudits()
        val auditsFiltered = audits.filterByAddressId(addressId = addresses.first().id)

        assertEquals(1, auditsFiltered.size)
    }

    @Test
    fun testGroups() {
        val addresses = getAddresses()
        val addressesGroupById = addresses.groupById()

        assertEquals(3, addressesGroupById.entries.size)
        assertEquals(1, addressesGroupById.get(addresses.first().id)?.size)

        val addressesAuditMetadata = getAddressesAudits()
        val auditsGroupByAddressId = addressesAuditMetadata.groupByAddressId()

        assertEquals(2, auditsGroupByAddressId.size)
        assertNull(auditsGroupByAddressId.get(addresses.last().id))
    }

    @Test
    fun testMergeData() {
        val addresses = getAddresses()
        val addressesAuditMetadata = getAddressesAudits()

        val addressesWithAudits = addresses.mergeAuditData(addressesAuditMetadata)

        assertEquals(3, addressesWithAudits.size)
        assertFalse(addressesWithAudits.get(0).listAuditMetadata!!.isEmpty())
        assertFalse(addressesWithAudits.get(1).listAuditMetadata!!.isEmpty())
        assertTrue(addressesWithAudits.get(2).listAuditMetadata!!.isEmpty())
    }
}