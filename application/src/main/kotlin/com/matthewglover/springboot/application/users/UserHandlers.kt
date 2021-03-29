package com.matthewglover.springboot.application.users

import com.matthewglover.springboot.application.NewUser
import com.matthewglover.springboot.application.User
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

class UserHandlers(private val userRepository: UserRepository) {

    suspend fun add(request: ServerRequest): ServerResponse {
        val newUser = request.awaitBody<NewUser>()
        val user = userRepository.add(newUser)

        return ok().bodyValueAndAwait(user)
    }

    @Suppress("UnusedPrivateMember")
    suspend fun all(request: ServerRequest): ServerResponse =
        ok().bodyValueAndAwait(userRepository.all())

    suspend fun delete(request: ServerRequest): ServerResponse {
        val userId = request.pathVariable("userId").toInt()
        userRepository.delete(userId)

        return status(HttpStatus.NO_CONTENT).buildAndAwait()
    }

    suspend fun get(request: ServerRequest): ServerResponse {
        val userId = request.pathVariable("userId").toInt()
        val user = userRepository.get(userId)

        return user?.let { ok().bodyValueAndAwait(it) } ?: badRequest().buildAndAwait()
    }

    suspend fun update(request: ServerRequest): ServerResponse {
        val user = request.awaitBody<User>()
        userRepository.update(user)

        return status(HttpStatus.NO_CONTENT).buildAndAwait()
    }
}
