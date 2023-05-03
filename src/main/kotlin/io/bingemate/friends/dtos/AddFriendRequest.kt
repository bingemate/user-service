package io.bingemate.friends.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.util.*

@Schema(name = "AddFriendRequest", description = "Request to add a friend")
data class AddFriendRequest (
    @JsonProperty("friendId")
    val friendId: UUID
)