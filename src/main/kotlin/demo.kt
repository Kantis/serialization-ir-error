import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonObject

@Serializable(with = OrganizationSerializer::class)
data class Organization(
    val id: Long,
    val name: String,
    @SerialName("address_route")
    val addressRoute: String?,
    @SerialName("address_street_number")
    val addressStreetNumber: String?,
    @SerialName("address_sublocality")
    val addressSublocality: String?,
    @SerialName("address_postal_code")
    val postalCode: String?,
    @SerialName("address_country")
    val country: String?,

    // OrganizationFields
    val email: String,
    val organizationNumber: String,
    val bankgiro: String? = null,
)

class OrganizationSerializer(private val fields: Map<String, String>) :
    JsonTransformingSerializer<Organization>(Organization.serializer()) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        println("** Running transformDeserialize")
        if (element !is JsonObject) return element
        return JsonObject(
            element.jsonObject.entries.associate { (key, value) ->
                if (key in fields.keys) fields[key]!! to value
                else key to value
            }
        )
    }
}
