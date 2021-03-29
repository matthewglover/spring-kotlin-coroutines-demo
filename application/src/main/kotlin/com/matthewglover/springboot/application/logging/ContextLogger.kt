package com.matthewglover.springboot.application.logging

import com.matthewglover.springboot.application.context.DefaultTags
import com.matthewglover.springboot.application.context.RequestContext
import com.matthewglover.springboot.application.context.elapsedMillis
import com.matthewglover.springboot.application.context.defaultTags
import com.matthewglover.springboot.application.context.requestContext
import com.matthewglover.springboot.application.context.context
import com.matthewglover.springboot.application.context.withElapsedMillis
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.message.Message
import org.apache.logging.log4j.message.StringMapMessage

typealias InjectLogContextConsumer = (DefaultTags, RequestContext) -> Unit

typealias MessageSupplier = (DefaultTags, RequestContext) -> Message

typealias MapSupplier = () -> Map<String, String>

class ContextLogger<T>(clazz: Class<T>, val systemTimeSupplier: () -> Long = System::nanoTime) {

    private val logger = LogManager.getLogger(clazz)

    @ExperimentalCoroutinesApi
    suspend fun info(mapSupplier: MapSupplier): Unit {
        info { defaultTags, _ -> StringMapMessage(defaultTags.putAll(mapSupplier())) }
    }

    @ExperimentalCoroutinesApi
    suspend fun info(messageSupplier: MessageSupplier): Unit {
        if (logger.isInfoEnabled) {
            injectLogContext { defaultTags, requestContext ->
                logger.info { messageSupplier(defaultTags, requestContext) }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun injectLogContext(consumer: InjectLogContextConsumer): Unit {
        context().let { context ->
            context.requestContext().let { requestContext ->
                consumer(
                    context.defaultTags().withElapsedMillis(
                        requestContext.startTime.elapsedMillis(systemTimeSupplier())
                    ),
                    requestContext
                )
            }
        }
    }
}
