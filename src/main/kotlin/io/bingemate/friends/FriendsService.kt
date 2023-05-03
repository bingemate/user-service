package io.bingemate.friends

import io.bingemate.friends.dtos.AddFriendRequest
import io.bingemate.friends.dtos.FriendDto
import io.bingemate.friends.dtos.UpdateFriendRequest
import io.bingemate.friends.entities.Friend
import io.bingemate.friends.models.FriendState
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.BadRequestException

@Singleton
class FriendsService {
    @Inject
    lateinit var friendsRepository: FriendsRepository

    fun getFriends(userId: UUID): List<FriendDto> {
        val friends: List<Friend> = friendsRepository.getFriends(userId)
        return friends.map { friend -> friend.toDto(userId) }
    }

    fun createFriendRequest(userId: UUID, addFriendRequest: AddFriendRequest): FriendDto {
        /// TODO : Check if friend existe
        val friendExist = friendsRepository.getRelationship(userId, addFriendRequest.friendId)
        if (friendExist != null) {
            throw BadRequestException("Friendship already exists")
        }
        val friend = Friend(
            state = FriendState.REQUESTED,
            userRequester = userId,
            userRequested = addFriendRequest.friendId
        )
        friendsRepository.persist(friend)
        return friend.toDto(userId)
    }

    fun updateFriendRequest(userId: UUID, updateFriendRequest: UpdateFriendRequest): FriendDto {
        /// TODO : Check if friend existe
        val friend = friendsRepository.getRelationship(userId, updateFriendRequest.friendId)
            ?: throw BadRequestException("Friendship does not exist")

        if (friend.state == updateFriendRequest.state) {
            throw BadRequestException("Friendship already in this state")
        }

        friend.state = updateFriendRequest.state
        friendsRepository.persist(friend)
        return friend.toDto(userId)
    }

    fun deleteFriendRequest(userId: UUID, friendId: UUID) {
        val friend = friendsRepository.getRelationship(userId, friendId)
            ?: throw BadRequestException("Friendship does not exist")

        friendsRepository.delete(friend)
    }
}