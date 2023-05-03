package io.bingemate.friends.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.bingemate.friends.models.FriendState
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.util.UUID

@Schema(name = "UpdateFriendRequest", description = "Request to update a friend")
data class UpdateFriendRequest(
    @JsonProperty("friendId")
    val friendId: UUID,

    @JsonProperty("state")
    val state: FriendState
)