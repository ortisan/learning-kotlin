package ortisan.algo.merge.bdd

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import org.junit.jupiter.api.Assertions.assertEquals
import ortisan.algo.merge.Address
import ortisan.algo.merge.AddressType
import ortisan.algo.merge.mergeData

class Steps : En {

    init {

        var inputAddresses: List<Address>? = ArrayList()
        var alreadyExistentAddresses: List<Address>? = ArrayList()

        Before { scenario: Scenario ->
            inputAddresses = ArrayList()
            alreadyExistentAddresses = ArrayList()
        }

        DataTableType { entry: Map<String, String> ->
            val line1 = entry["line1"]!!
            val types = entry["types"]!!.split(",")?.map { AddressType.valueOf(it) }.toList()
            val completeness = entry["completeness"]!!.toInt()
            Address(
                id = null,
                line1,
                types,
                completeness
            )
        }

        Given("Im creating addresses as following") { addressesTable: DataTable ->
            val addresses: List<Address> = addressesTable.asList(Address::class.java)
            inputAddresses = addresses
        }

        When("I already have these addresses") { addressesTable: DataTable ->
            val addresses: List<Address> = addressesTable.asList(Address::class.java)
            alreadyExistentAddresses = addresses
        }

        When("I have no addresses already inserted") {
            alreadyExistentAddresses = ArrayList()
        }

        Then("I have {int} new addresses, {int} updated addresses and {int} new diffs addresses") { newAddresses: Int, updatedAddresses: Int, openDiffsAddresses: Int ->
            val (newAddressesResult, updateAddressesResult, openDiffResult) = mergeData(
                inputAddresses!!,
                alreadyExistentAddresses!!
            )
            assertEquals(newAddresses, newAddressesResult!!.size)
            assertEquals(updatedAddresses, updateAddressesResult!!.size)
            assertEquals(openDiffsAddresses, openDiffResult!!.size)
        }
    }
}