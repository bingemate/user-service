package io.bingemate.user_preferences

import io.bingemate.user_preferences.dtos.CreateUserPreferencesDto
import io.bingemate.user_preferences.dtos.UpdateUserPreferencesDto
import io.bingemate.user_preferences.dtos.UserPreferencesDto
import io.bingemate.user_preferences.dtos.WhatUserCanSeeDto
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

@Path("/user-preferences")
@Tag(name = "User Preferences")
class UserPreferencesResource {
    @Inject
    lateinit var userPreferencesService: UserPreferencesService

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get user preferences", description = "Get the user preferences of the user, creates a new one if it doesn't exist")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "Returns the user preferences",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            type = SchemaType.OBJECT,
                            implementation = UserPreferencesDto::class
                        ),
                    )
                ]
            )
        ],
    )
    fun getUserPreferences(
        @HeaderParam("User-id") userId: UUID,
    ): Response {
        return Response.ok(userPreferencesService.getUserPreferences(userId)).build()
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create user preferences", description = "Create the user preferences of the user")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "201",
                description = "Returns the user preferences",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            type = SchemaType.OBJECT,
                            implementation = UserPreferencesDto::class
                        ),
                    )
                ]
            )
        ],
    )
    fun createUserPreferences(
        @HeaderParam("User-id") userId: UUID,
        createUserPreferencesDto: CreateUserPreferencesDto,
    ): Response {
        val userPreferences = userPreferencesService.createUserPreferences(userId, createUserPreferencesDto)
        return Response.status(Response.Status.CREATED).entity(userPreferences).build()
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update user preferences", description = "Update the user preferences of the user")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "Returns the user preferences",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            type = SchemaType.OBJECT,
                            implementation = UserPreferencesDto::class
                        ),
                    )
                ]
            )
        ],
    )
    fun updateUserPreferences(
        @HeaderParam("User-id") userId: UUID,
        updateUserPreferencesDto: UpdateUserPreferencesDto,
    ): Response {
        print(updateUserPreferencesDto)
        val userPreferences = userPreferencesService.updateUserPreferences(userId, updateUserPreferencesDto)
        return Response.ok(userPreferences).build()
    }

    @DELETE
    @Transactional
    @Operation(summary = "Delete user preferences", description = "Delete the user preferences of the user")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "User preferences deleted",
            )
        ],
    )
    fun deleteUserPreferences(
        @HeaderParam("User-id") userId: UUID,
    ): Response {
        userPreferencesService.deleteUserPreferences(userId)
        return Response.ok().build()
    }

    @GET
    @Path("/{targetUserId}")
    @Operation(summary = "Get authorized user preferences", description = "Get the user preferences of the user if the user is authorized")
    @APIResponses(
        value = [
            APIResponse(
                responseCode = "200",
                description = "Returns the user preferences",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(
                            type = SchemaType.OBJECT,
                            implementation = WhatUserCanSeeDto::class
                        ),
                    )
                ]
            )
        ],
    )
    fun getAuthorizedUserPreferences(
        @HeaderParam("User-id") userId: UUID,
        @PathParam("targetUserId") targetUserId: UUID,
    ): Response {
        val whatUserCanSee = userPreferencesService.whatUserCanSee(userId, targetUserId)
        return Response.ok(whatUserCanSee).build()
    }
}