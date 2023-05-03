package io.bingemate.user_preferences

import io.bingemate.user_preferences.entities.UserPreferences
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserPreferencesRepository : PanacheRepository<UserPreferences> {
    fun findByUserId(userId: UUID): UserPreferences? {
        return find("userId", userId).firstResult()
    }
}