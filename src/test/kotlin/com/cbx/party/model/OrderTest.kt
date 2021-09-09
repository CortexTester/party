package com.cbx.party.model

import cbx.ubl.AdditionalProperty
import cbx.ubl.Address
import cbx.ubl.AllowanceCharge
import cbx.ubl.Amount
import cbx.ubl.Attachment
import cbx.ubl.Code
import cbx.ubl.Contact
import cbx.ubl.Contract
import cbx.ubl.CurrencyCode
import cbx.ubl.Delivery
import cbx.ubl.DeliveryTerm
import cbx.ubl.DocumentReference
import cbx.ubl.EmbeddedDocumentBinaryObject
import cbx.ubl.EnergyCostCode
import cbx.ubl.Id
import cbx.ubl.Item
import cbx.ubl.LineItem
import cbx.ubl.Location
import cbx.ubl.MonetaryTotal
import cbx.ubl.Order
import cbx.ubl.Party
import cbx.ubl.PartyIdSchemeIdentifier
import cbx.ubl.PartyLegalEntity
import cbx.ubl.PartyTaxScheme
import cbx.ubl.Period
import cbx.ubl.Person
import cbx.ubl.Price
import cbx.ubl.Quantity
import cbx.ubl.ReferenceSchemeIdentifier
import cbx.ubl.TaxTotal
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.time.OffsetDateTime
import java.time.ZoneOffset
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.boot.test.autoconfigure.json.JsonTest

//@JsonTest
class OrderTest {
    //    @Autowired
//    lateinit var jacksonTester: JacksonTester<OffsetDateTime>

    val objectMapper =
        ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(StdDateFormat().withColonInTimeZone(true))
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    val zoneOffSet = ZoneOffset.of("+07:00")
    @Test
    fun `OffsetDateTime json convert`() {
        var issueDateTime = OffsetDateTime.now( ZoneOffset.of("-06:00"))
        val actualString = objectMapper.writeValueAsString(issueDateTime)

        println(actualString) // 2018-02-06T08:37:33.557-08:00

        val actual = objectMapper.readValue(actualString, OffsetDateTime::class.java)
        assertThat(actual).isNotNull()
    }

    @Test
    fun `convert json order to order object`(){
        val order = getTestOrder()
        val orderString = objectMapper.writeValueAsString(order)

        val actual = objectMapper.readValue(orderString, Order::class.java)
        assertThat(actual).isNotNull()
    }


    fun getTestOrder() = Order(
        cbxVersionId = "0.1",
        orderNumber = "test order 01",
        issueDateTime = OffsetDateTime.now(zoneOffSet),//LocalDateTime.of(2021, 8,11, 11, 10, 42),
        note = "This is Order type unit test",
        documentCurrencyCode = CurrencyCode.USD,
        accountingCostCode = listOf(
            Code(
                codeContent = "AFE1",
                codeListAgencyIdentifier = EnergyCostCode.AFE.toString()
            )
        ),
        validityPeriod = Period(
            startDate = OffsetDateTime.now(zoneOffSet),//LocalDateTime.of(2021, Month.AUGUST, 2, 0, 0, 0, 0),
            endDate = OffsetDateTime.now(zoneOffSet)//LocalDateTime.of(2021, Month.NOVEMBER, 2, 0, 0, 0, 0)
        ),
        quotationDocumentReference = listOf(
            DocumentReference(
                id = Id(
                    idContent = "test quotation 01",
                    idSchemeIdentifier = ReferenceSchemeIdentifier.CbxQuotationNumber.toString()
                )
            ),
            DocumentReference(
                id = Id(
                    idContent = "1",
                    idSchemeIdentifier = ReferenceSchemeIdentifier.CbxQuotationId.toString()
                )
            )
        ),
        orderDocumentReference = listOf(DocumentReference(id = Id(idContent = "RejectedOrder123"))),
        originatorDocumentReference = listOf(DocumentReference(id = Id(idContent = "MAFO"))),
        additionalDocumentReference = listOf(
            DocumentReference(
                id = Id(idContent = "Att01"), documentType = "timesheet", attachment = listOf(
                    Attachment(externalReference = "thhps://local.cbx.com/att/att01.pdf"),
                    Attachment(
                        EmbeddedDocumentBinaryObject(
                            binaryObjectMimeCode = "application/pdf",
                            binaryObjectContent = "UjBsR09EbGhjZ0dTQUxNQUFBUUNBRU1tQ1p0dU1GUXhEUzhi"
                        )
                    )
                )
            )
        ),
        contract = listOf(Contract(id = Id(idContent = "test contract 01"))),
        buyerCustomerParty = Party(
            partyIdentification = listOf(
                Id(
                    idContent = "party01", //todo: correct as cbx int id
                    idSchemeIdentifier = PartyIdSchemeIdentifier.CBX.toString()
                ),
                Id(
                    idContent = "987456321",
                    idSchemeIdentifier = PartyIdSchemeIdentifier.DUNS.toString()
                )
            ),
            partyName = "party01",
            postalAddress = listOf(
                getAddress()
            ),
            partyTaxScheme = listOf(
                PartyTaxScheme(
                    companyId = Id(idContent = "SE1234567801"),
                    taxScheme = Id(idContent = "AVT", idSchemeIdentifier = "UN/ECE 5153", idAgencyIdentifier = "6")
                )
            ),
            partyLegalEntity = listOf(
                PartyLegalEntity(
                    companyId = Id(
                        idContent = "5512895671",
                        idSchemeIdentifier = "Alberta Business Registry",
                        idAgencyIdentifier = "12"
                    ),
                    registrationName = "Alpine Service Inc.",
                    registrationAddress = getAddress()
                )
            ),
            contact = listOf(Contact(telephone = "403-123-4567", email = "al@cbx.com")),
            person = listOf(Person(firstName = "tester")),
        ),
        sellerSupplierParty = Party(
            partyIdentification = listOf(
                Id(
                    idContent = "party02", //todo: correct as cbx int id
                    idSchemeIdentifier = PartyIdSchemeIdentifier.CBX.toString()
                ),
                Id(
                    idContent = "123456789",
                    idSchemeIdentifier = PartyIdSchemeIdentifier.DUNS.toString()
                )
            ),
            partyName = "party02"
        ),
        delivery = listOf(
            Delivery(
                deliveryLocation = Location(address = getAddress()),
                deliveryParty = Party(
                    partyName = "Swedish trucking", partyIdentification = listOf(
                        Id(idContent = "Party03", idSchemeIdentifier = PartyIdSchemeIdentifier.CBX.toString())
                    )
                ),
                deliveryTerms = listOf(DeliveryTerm(specialTerms = "FOT"))
            )
        ),
        allowanceCharge = listOf(
            AllowanceCharge(
                allowanceChargeReason = "Transport documents",
                Amount(amountContent = 100.00)
            )
        ),
        taxTotal = listOf(TaxTotal(taxAmount = Amount(100.00))),
        anticipatedMonetaryTotal = MonetaryTotal(
            lineExtensionAmount = Amount(amountContent = 6225.00),
            allowanceTotalAmount = Amount(amountContent = 100.00),
            chargeTotalAmount = Amount(amountContent = 100.00),
            payableAmount = Amount(amountContent = 6225.00)
        ),
        orderLine = listOf(
            LineItem(
                id = Id(idContent = "1"),
                item = Item(
                    name = "Hauling Service",
                    description = "ship water to fields",
                    additionalItemProperty = listOf(
                        AdditionalProperty(
                            name = "Service Type",
                            value = "Truck  Solvant"
                        )
                    )
                ),
                quantity = Quantity(quantityContent = 1, quantityUnitCode = "Hour"),
                price = Price(priceAmount = Amount(amountContent = 50.00)),
            )
        )
    )

    fun getAddress() = Address(
        buildingNumber = "123",
        streetName = "8 Ave SW",
        cityName = "Calgary",
        countrySubEntity = "Alberta",
        country = "Canada"
    )
}
