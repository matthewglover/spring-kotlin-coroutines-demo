package com.matthewglover.springboot.application.context

import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactor.ReactorContext
import reactor.util.context.Context

private object Errors {
    val REACTOR_CONTEXT_READ =
        RuntimeException("Attempting to read ReactorContext but no ReactorContext set")
    val REQUEST_CONTEXT_READ =
        RuntimeException("Attempting to read RequestContext but no RequestContext set")
    val DEFAULT_TAGS_READ =
        RuntimeException("Attempting to read DefaultTags but no DefaultTags set")
}

@ExperimentalCoroutinesApi
suspend fun context(): Context =
    coroutineContext[ReactorContext]?.context ?: throw Errors.REACTOR_CONTEXT_READ

fun Context.requestContext(): RequestContext =
    getOrEmpty<RequestContext>(RequestContext::class).orElseThrow { Errors.REQUEST_CONTEXT_READ }

fun Context.defaultTags(): DefaultTags =
    getOrEmpty<DefaultTags>(DefaultTags::class).orElseThrow { Errors.DEFAULT_TAGS_READ }
