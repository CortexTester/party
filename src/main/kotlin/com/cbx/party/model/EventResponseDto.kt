package com.cbx.party.model

data class EventResponseDto(
    var trackingId: String,
    var contents: MutableList<ContentResponseDto>
)

data class ContentResponseDto(
    var locationId: Long,
    var fileName: String,
    var fileType: String,
    var size: Long,
)
