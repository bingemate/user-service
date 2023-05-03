package io.bingemate.user_preferences.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.bingemate.user_preferences.models.VisibilityPreferences
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(name = "UpdateUserPreferences", description = "The preferences of a user")
data class UpdateUserPreferencesDto(
    @JsonProperty("statsVisibility")
    val statsVisibility: VisibilityPreferences? = null,

    @JsonProperty("readingHistoryVisibility")
    val readingHistoryVisibility: VisibilityPreferences? = null,
)
