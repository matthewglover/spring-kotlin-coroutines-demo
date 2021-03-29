package com.matthewglover.springboot.application.handlerfilters

import com.matthewglover.springboot.application.logging.ContextLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.apache.logging.log4j.message.StringMapMessage
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

class RequestResponseLoggerFilter(private val systemTimeSupplier: () -> Long = System::nanoTime) {

    private val logger = ContextLogger(RequestResponseLoggerFilter::class.java)

    @ExperimentalCoroutinesApi
    suspend fun apply(
        serverRequest: ServerRequest,
        handler: suspend (ServerRequest) -> ServerResponse
    ): ServerResponse {
        logger.info { -> mapOf("message" to "starting request") }

        val response = handler(serverRequest)

        logger.info { -> mapOf("status" to response.statusCode().toString(), "message" to "request completed") }

        return response
    }
}
