package io.bingemate.user_preferences.entities

import io.bingemate.user_preferences.dtos.UserPreferencesDto
import io.bingemate.user_preferences.models.VisibilityPreferences
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_preferences")
data class UserPreferences (
    @Id
    @GeneratedValue(generator = "UUID")
    var id: UUID? = null,
    var userId: UUID? = null,
    var statsVisibility: VisibilityPreferences = VisibilityPreferences.PRIVATE,
    var readingHistoryVisibility: VisibilityPreferences = VisibilityPreferences.PRIVATE
) {
    fun toDto(): UserPreferencesDto {
        return UserPreferencesDto(
            statsVisibility = this.statsVisibility,
            readingHistoryVisibility = this.readingHistoryVisibility
        )
    }
}