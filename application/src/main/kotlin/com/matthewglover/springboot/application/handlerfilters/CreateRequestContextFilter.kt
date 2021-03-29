package com.matthewglover.springboot.application.handlerfilters

import com.matthewglover.springboot.application.context.RequestContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactor.asCoroutineContext
import kotlinx.coroutines.withContext
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.util.context.Context

class CreateRequestContextFilter(private val systemTimeSupplier: () -> Long = System::nanoTime) {

    @ExperimentalCoroutinesApi
    suspend fun apply(
        serverRequest: ServerRequest,
        handler: suspend (ServerRequest) -> ServerResponse
    ): ServerResponse {
        val startTime = systemTimeSupplier()
        val method = serverRequest.methodName()
        val path = serverRequest.path()
        val requestContext = RequestContext(startTime, method, path)
        return withContext(Context.of(RequestContext::class, requestContext).asCoroutineContext()) {
            handler(serverRequest)
        }
    }
}
