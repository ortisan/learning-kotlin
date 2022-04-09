package algo.merge.bdd.step

import algo.merge.Address
import algo.merge.AddressType
import algo.merge.mergeData
import io.cucumber.java.Before
import io.cucumber.java.DataTableType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals


class Steps  {

    private var inputAddresses: List<Address>? = ArrayList()
    private var alreadyExistentAddresses: List<Address>? = ArrayList()

    @Before
    fun setUp() {
        inputAddresses = ArrayList()
        alreadyExistentAddresses = ArrayList()
    }

    @DataTableType
    fun addressEntry(entry: Map<String, String>): Address {
        val line1 = entry["line1"]!!
        val types = entry["types"]!!.split(",")?.map { AddressType.valueOf(it) }.toList()
        val completeness = entry["completeness"]!!.toInt()
        return Address(
            id = null,
            line1,
            types,
            completeness
        )
    }

    @Given("Im creating addresses as following")
    fun inputAddresses(addresses: List<Address>) {
        this.inputAddresses = addresses
    }

    @When("I already have these addresses")
    fun alreadyExistingAddresses(addresses: List<Address>) {
        this.alreadyExistentAddresses = addresses
    }

    @Then("I have {integer} new addresses, {integer} updated addresses and {integer} new diffs addresses")
    fun i_should_have(newAddresses: Int, updatedAddresses: Int, openDiffsAddresses: Int) {

        val (newAddressesResult, updateAddressesResult, openDiffResult) = mergeData(
            this.inputAddresses!!,
            this.alreadyExistentAddresses!!
        )
        assertEquals(newAddresses, newAddressesResult!!.size)
        assertEquals(updatedAddresses, updateAddressesResult!!.size)
        assertEquals(newAddresses, openDiffResult!!.size)
    }
}