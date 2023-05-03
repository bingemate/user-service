package io.bingemate.friends.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import io.bingemate.friends.models.FriendState
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.util.UUID

@Schema(name = "FriendDto", description = "A friend of a user")
data class FriendDto (
    @JsonProperty("state")
    val state: FriendState,

    @JsonProperty("friendId")
    val friendId: UUID,
)