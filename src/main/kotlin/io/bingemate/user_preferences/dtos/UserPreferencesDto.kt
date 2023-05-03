package io.bingemate.user_preferences.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.bingemate.user_preferences.models.VisibilityPreferences
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.util.UUID

@Schema(name = "UserPreferencesDto", description = "The preferences of a user")
data class UserPreferencesDto (
    @JsonProperty("statsVisibility")
    val statsVisibility: VisibilityPreferences,

    @JsonProperty("readingHistoryVisibility")
    val readingHistoryVisibility: VisibilityPreferences,
)