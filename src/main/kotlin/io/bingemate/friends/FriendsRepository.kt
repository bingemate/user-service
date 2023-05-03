package io.bingemate.friends

import io.bingemate.friends.entities.Friend
import io.bingemate.friends.models.FriendState
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import org.jboss.resteasy.annotations.Query
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FriendsRepository : PanacheRepository<Friend> {
    fun getFriends(userId: UUID): List<Friend> {
        val params = mapOf("userId" to userId, "state" to FriendState.ACCEPTED)
        return find(
            "(userRequester = :userId or userRequested = :userId) and state = :state",
            params
        ).list()
    }

    fun getRelationship(userId: UUID, friendId: UUID): Friend? {
        val params = mapOf("userId" to userId, "friendId" to friendId)
        return find(
            "(userRequester = :userId and userRequested = :friendId) or (userRequester = :friendId and userRequested = :userId)",
            params
        ).firstResult()
    }

}