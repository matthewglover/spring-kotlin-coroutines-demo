package com.matthewglover.springboot.application

import com.matthewglover.springboot.application.context.DefaultTags
import com.matthewglover.springboot.application.context.RequestContext
import com.matthewglover.springboot.application.context.requestContext
import com.matthewglover.springboot.application.context.context
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactor.asCoroutineContext
import kotlinx.coroutines.withContext
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse


object CreateDefaultTagsFilter {

    @ExperimentalCoroutinesApi
    suspend fun apply(
        serverRequest: ServerRequest,
        handler: suspend (ServerRequest) -> ServerResponse
    ): ServerResponse {
        return context().let { context ->
            context.requestContext()?.let { requestContext ->
                withContext(
                    context
                        .put(DefaultTags::class, defaultTagsFrom(requestContext))
                        .asCoroutineContext()
                ) { handler(serverRequest) }
            }
        }
            ?: throw RuntimeException("Could not create DefaultTags, missing RequestContext")
    }

    private fun defaultTagsFrom(requestContext: RequestContext): DefaultTags =
        DefaultTags(
            persistentMapOf(
                "method" to requestContext.method,
                "path" to requestContext.path,
            )
        )
}
