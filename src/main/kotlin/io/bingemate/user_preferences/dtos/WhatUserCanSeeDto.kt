package io.bingemate.user_preferences.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "WhatUserCanSeeDto", description = "Access rights of a user")
data class WhatUserCanSeeDto(

    @JsonProperty("statsVisibility")
    val statsVisibility: Boolean,

    @JsonProperty("readingHistoryVisibility")
    val readingHistoryVisibility: Boolean
)
