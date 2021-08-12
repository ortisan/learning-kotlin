import algo.merge.Address
import algo.merge.AddressType
import algo.merge.FieldAddress
import algo.merge.mergeData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class MergeAlgoTest {

    @Test
    fun testMerge() {
        val idAddress1 = UUID.randomUUID()
        val idAddress2 = UUID.randomUUID()
        val idAddress3 = UUID.randomUUID()

        val address1 = Address(id = idAddress1.toString(), addressType = AddressType.RESIDENTIAL, line1 = "Av. Paulista 1", zipcode = "123123")
        val address2 = Address(id = idAddress2.toString(), addressType = AddressType.BUSINESS, line1 = "Rua do Carmo 2", zipcode = "456456")
        val address3 = Address(id = idAddress3.toString(), addressType = AddressType.OTHER, line1 = "Rua Bonifácio 3", zipcode = "789789")

        val addressDiffOpen = Address(id = idAddress3.toString(), addressType = AddressType.RESIDENTIAL)

        val addressesGoldenSource = Arrays.asList(
                FieldAddress(newValue = address1, actualValue = address1),
                FieldAddress(newValue = address2, actualValue = address2),
                FieldAddress(newValue = address3, actualValue = address3)
        )

        val addressFromDiff = Arrays.asList(
                FieldAddress(newValue = addressDiffOpen, actualValue = address1)
        )

        val addressesMerged = mergeData(addressesGoldenSource, addressFromDiff)

        Assertions.assertEquals(2, addressesMerged.size)
        val addressByType = addressesMerged.groupBy { it.addressType }
        Assertions.assertEquals(address3.id, addressByType.get(AddressType.RESIDENTIAL)!!.get(0).id)
    }
}