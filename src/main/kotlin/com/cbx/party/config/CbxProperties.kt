package com.cbx.party.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "cbx")
@ConstructorBinding
data class CbxProperties(
    var partyId: Long,
    var partyApiKey: String,
    var partyBaseUrl: String,
    var partyDialect: String,
    var hubBaseUrl: String
)

