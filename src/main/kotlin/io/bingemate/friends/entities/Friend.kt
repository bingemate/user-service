package io.bingemate.friends.entities

import io.bingemate.friends.dtos.FriendDto
import io.bingemate.friends.models.FriendState
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "friends")
data class Friend (
    @Id
    @GeneratedValue(generator = "UUID")
    var id: UUID? = null,

    var state: FriendState? = null,

    var userRequester: UUID? = null,

    var userRequested: UUID? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null
) {
    fun toDto(userId: UUID): FriendDto {
        if (this.userRequester == userId) {
            return FriendDto(
                state = this.state!!,
                friendId = this.userRequested!!,
                requester = true
            )
        }
        return FriendDto(
            state = this.state!!,
            friendId = this.userRequester!!,
            requester = false
        )
    }
}