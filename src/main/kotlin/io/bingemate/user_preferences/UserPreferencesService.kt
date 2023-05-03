package io.bingemate.user_preferences

import io.bingemate.friends.FriendsService
import io.bingemate.friends.models.FriendState
import io.bingemate.user_preferences.dtos.CreateUserPreferencesDto
import io.bingemate.user_preferences.dtos.UpdateUserPreferencesDto
import io.bingemate.user_preferences.dtos.UserPreferencesDto
import io.bingemate.user_preferences.dtos.WhatUserCanSeeDto
import io.bingemate.user_preferences.entities.UserPreferences
import io.bingemate.user_preferences.models.VisibilityPreferences
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.BadRequestException

@Singleton
class UserPreferencesService {
    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @Inject
    lateinit var friendService: FriendsService

    fun createUserPreferences(userId: UUID, createUserPreferencesDto: CreateUserPreferencesDto): UserPreferencesDto {
        val userPreferences = UserPreferences(
            userId = userId,
            statsVisibility = createUserPreferencesDto.statsVisibility,
            readingHistoryVisibility = createUserPreferencesDto.readingHistoryVisibility
        )
        userPreferencesRepository.persist(userPreferences)
        return userPreferences.toDto()
    }

    fun getUserPreferences(userId: UUID): UserPreferencesDto {
        val userPreferences = userPreferencesRepository.findByUserId(userId)
            ?: return createUserPreferences(
                userId,
                CreateUserPreferencesDto(
                    statsVisibility = VisibilityPreferences.PRIVATE,
                    readingHistoryVisibility = VisibilityPreferences.PRIVATE
                )
            )
        return userPreferences.toDto()
    }

    fun updateUserPreferences(userId: UUID, updateUserPreferencesDto: UpdateUserPreferencesDto): UserPreferencesDto {
        val userPreferences = userPreferencesRepository.findByUserId(userId)
            ?: return createUserPreferences(
                userId,
                CreateUserPreferencesDto(
                    statsVisibility = VisibilityPreferences.PRIVATE,
                    readingHistoryVisibility = VisibilityPreferences.PRIVATE
                )
            )
        if (updateUserPreferencesDto.statsVisibility == null && updateUserPreferencesDto.readingHistoryVisibility == null) {
            throw BadRequestException("At least one of the fields must be set")
        }
        if (updateUserPreferencesDto.statsVisibility != null) {
            userPreferences.statsVisibility = updateUserPreferencesDto.statsVisibility
        }
        if (updateUserPreferencesDto.readingHistoryVisibility != null) {
            userPreferences.readingHistoryVisibility = updateUserPreferencesDto.readingHistoryVisibility
        }
        userPreferencesRepository.persist(userPreferences)
        return userPreferences.toDto()
    }

    fun deleteUserPreferences(userId: UUID) {
        val userPreferences = userPreferencesRepository.findByUserId(userId)
            ?: throw BadRequestException("User preferences does not exist")
        userPreferencesRepository.delete(userPreferences)
    }

    fun whatUserCanSee(userId: UUID, targetUserId: UUID): WhatUserCanSeeDto {
        val targetUserPreferences = this.getUserPreferences(targetUserId)
        val relationship = this.friendService.getFriends(targetUserId).find { friend -> friend.friendId == userId }
        val isFriend = relationship != null && relationship.state == FriendState.ACCEPTED

        val statsVisibility = if (targetUserPreferences.statsVisibility == VisibilityPreferences.PUBLIC) {
            true
        } else targetUserPreferences.statsVisibility == VisibilityPreferences.FRIENDS && isFriend

        val readingHistoryVisibility =
            if (targetUserPreferences.readingHistoryVisibility == VisibilityPreferences.PUBLIC) {
                true
            } else targetUserPreferences.readingHistoryVisibility == VisibilityPreferences.FRIENDS && isFriend

        return WhatUserCanSeeDto(
            statsVisibility = statsVisibility,
            readingHistoryVisibility = readingHistoryVisibility
        )
    }
}