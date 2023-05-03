package io.bingemate.friends

import io.bingemate.friends.dtos.AddFriendRequest
import io.bingemate.friends.dtos.FriendDto
import io.bingemate.friends.dtos.UpdateFriendRequest
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.resteasy.annotations.jaxrs.HeaderParam
import java.util.*
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/friends")
@Tag(name = "Friends")
class FriendsResource {

    @Inject
    lateinit var friendsService: FriendsService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get friends", description = "Get a list of friends of the user")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "Returns a list of friends",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            type = SchemaType.ARRAY,
                            implementation = FriendDto::class
                        ),
                    )
                ]
            )
        ],
    )
    fun getFriends(
        @HeaderParam("User-id") userId: UUID,
    ): Response {
        val friends = friendsService.getFriends(userId)
        return Response.ok(friends).build()
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create friend request", description = "Create a friend request")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "201",
                description = "Friend request created",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = FriendDto::class
                        ),
                    )
                ]
            ),
            APIResponse(
                responseCode = "400",
                description = "Bad request"
            )
        ],
    )
    fun createFriendRequest(
        @HeaderParam("User-id") userId: UUID,
        addFriendRequest: AddFriendRequest,
    ): Response {
        val friend = friendsService.createFriendRequest(userId, addFriendRequest)
        return Response.status(Response.Status.CREATED).entity(friend).build()
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update friend request", description = "Update a friend request")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "Friend request updated",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            implementation = UpdateFriendRequest::class
                        ),
                    )
                ]
            ),
            APIResponse(
                responseCode = "400",
                description = "Bad request"
            )
        ],
    )
    fun updateFriendRequest(
        @HeaderParam("User-id") userId: UUID,
        updateFriendRequest: UpdateFriendRequest,
    ): Response {
        val friend = friendsService.updateFriendRequest(userId, updateFriendRequest)
        return Response.status(Response.Status.OK).entity(friend).build()
    }

    @DELETE
    @Transactional
    @Path("/{friendId}")
    @Operation(summary = "Delete friend", description = "Delete a friend")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "Friend deleted",
            ),
            APIResponse(
                responseCode = "400",
                description = "Bad request"
            )
        ],
    )
    fun deleteFriendRequest(
        @HeaderParam("User-id") userId: UUID,
        @PathParam("friendId") friendId: UUID,
    ): Response {
        friendsService.deleteFriendRequest(userId, friendId)
        return Response.ok().build()
    }
}