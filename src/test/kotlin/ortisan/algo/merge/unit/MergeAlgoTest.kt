package ortisan.algo.merge.unit

import ortisan.algo.merge.Address
import ortisan.algo.merge.AddressType
import ortisan.algo.merge.mergeData
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class MergeAlgoTest {

    @Test
    fun mergeNewAddressesCompletenessHigher() {
        val idAddress1 = UUID.randomUUID()
        val idAddress2 = UUID.randomUUID()

        val paulistaResBusinessCompleteness400 = Address(
            id = idAddress1.toString(),
            line1 = "Av. Paulista",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL, AddressType.BUSINESS),
            400
        )
        val bonifacioOther400 = Address(
            id = idAddress2.toString(),
            line1 = "Rua Bonifácio",
            addressTypes = Arrays.asList(AddressType.OTHER),
            400
        )

        val newAddresses = listOf(paulistaResBusinessCompleteness400, bonifacioOther400)

        val moocaResBusinessCompleteness200 = Address(
            id = idAddress1.toString(),
            line1 = "Rua Mooca",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL, AddressType.BUSINESS),
            200
        )
        val goldenAddresses = listOf(moocaResBusinessCompleteness200)

        val (newAddressesResult, updateAddressesResult, openDiffResult) = mergeData(newAddresses, goldenAddresses)

        assertEquals(2, newAddressesResult?.size)
        assertTrue { newAddressesResult!![0].addressTypes == listOf(AddressType.RESIDENTIAL, AddressType.BUSINESS) }
        assertTrue { newAddressesResult!![1].addressTypes == listOf(AddressType.OTHER) }
        assertEquals(1, updateAddressesResult?.size)
        assertTrue { updateAddressesResult!![0].addressTypes == listOf(AddressType.OTHER) }
        assertEquals(0, openDiffResult?.size)
    }


    @Test
    fun mergeNewAddressesCompletenessLower() {
        val idAddress1 = UUID.randomUUID()
        val idAddress2 = UUID.randomUUID()

        val paulistaResBusinessCompleteness200 = Address(
            id = idAddress1.toString(),
            line1 = "Av. Paulista",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL, AddressType.BUSINESS),
            200
        )
        val bonifacioOther200 = Address(
            id = idAddress2.toString(),
            line1 = "Rua Bonifácio",
            addressTypes = Arrays.asList(AddressType.OTHER),
            200
        )

        val newAddresses = listOf(paulistaResBusinessCompleteness200, bonifacioOther200)

        val moocaResBusinessCompleteness400 = Address(
            id = idAddress1.toString(),
            line1 = "Rua Mooca",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL, AddressType.BUSINESS),
            400
        )
        val goldenAddresses = listOf(moocaResBusinessCompleteness400)

        val (newAddressesResult, updateAddressesResult, openDiffResult) = mergeData(newAddresses, goldenAddresses)

        assertEquals(2, newAddressesResult?.size)
        assertTrue { newAddressesResult!![0].addressTypes == listOf(AddressType.OTHER) }
        assertTrue { newAddressesResult!![1].addressTypes == listOf(AddressType.OTHER) }
        assertEquals(0, updateAddressesResult?.size)
        assertEquals(2, openDiffResult?.size)
    }

    @Test
    fun mergeNewAddressesCompletenessLowerAndHigher() {
        val idAddress1 = UUID.randomUUID()
        val idAddress2 = UUID.randomUUID()

        val paulistaResBusinessCompleteness300 = Address(
            id = idAddress1.toString(),
            line1 = "Av. Paulista",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL, AddressType.BUSINESS),
            300
        )

        val bonifacioOther200 = Address(
            id = idAddress2.toString(),
            line1 = "Rua Bonifácio",
            addressTypes = Arrays.asList(AddressType.OTHER),
            200
        )

        val newAddresses = listOf(paulistaResBusinessCompleteness300, bonifacioOther200)

        val saoFranciscoResCompleteness200 = Address(
            id = idAddress1.toString(),
            line1 = "Rua Mooca",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL),
            200
        )

        val moocaBusinessCompleteness400 = Address(
            id = idAddress1.toString(),
            line1 = "Av. Faria Lima",
            addressTypes = Arrays.asList(AddressType.BUSINESS),
            400
        )
        val goldenAddresses = listOf(saoFranciscoResCompleteness200, moocaBusinessCompleteness400)

        val (newAddressesResult, updateAddressesResult, openDiffResult) = mergeData(newAddresses, goldenAddresses)

        assertEquals(2, newAddressesResult?.size)
        assertTrue { newAddressesResult!![0].addressTypes.containsAll(listOf(AddressType.RESIDENTIAL, AddressType.OTHER)) }
        assertTrue { newAddressesResult!![1].addressTypes == listOf(AddressType.OTHER) }
        assertEquals(1, updateAddressesResult?.size)
        assertTrue { updateAddressesResult!![0].addressTypes == listOf(AddressType.OTHER) }
        assertEquals(1, openDiffResult?.size)
    }

    @Test
    fun mergeNewAddressesWithSameDataCompletenessLowerAndHigher() {
        val idAddress1 = UUID.randomUUID()
        val idAddress2 = UUID.randomUUID()

        val paulistaResBusinessCompleteness300 = Address(
            id = idAddress1.toString(),
            line1 = "Av. Paulista",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL, AddressType.BUSINESS),
            300
        )

        val bonifacioOther200 =
            Address(
                id = idAddress2.toString(),
                line1 = "Rua Bonifácio",
                addressTypes = Arrays.asList(AddressType.OTHER),
                200
            )

        val newAddresses = listOf(paulistaResBusinessCompleteness300, bonifacioOther200)

        val saoFranciscoResCompleteness200 = Address(
            id = idAddress1.toString(),
            line1 = "Rua Mooca",
            addressTypes = Arrays.asList(AddressType.RESIDENTIAL),
            200
        )

        val moocaBusinessCompleteness400 = Address(
            id = idAddress1.toString(),
            line1 = "Av. Faria Lima",
            addressTypes = Arrays.asList(AddressType.BUSINESS),
            400
        )
        val goldenAddresses = listOf(saoFranciscoResCompleteness200, moocaBusinessCompleteness400)

        val (newAddressesResult, updateAddressesResult, openDiffResult) = mergeData(newAddresses, goldenAddresses)

        assertEquals(2, newAddressesResult?.size)
        assertTrue { newAddressesResult!![0].addressTypes == listOf(AddressType.RESIDENTIAL, AddressType.OTHER) }
        assertTrue { newAddressesResult!![1].addressTypes == listOf(AddressType.OTHER) }
        assertEquals(1, updateAddressesResult?.size)
        assertTrue { updateAddressesResult!![0].addressTypes == listOf(AddressType.OTHER) }
        assertEquals(1, openDiffResult?.size)
    }
}