package com.matthewglover.springboot.application.context

import java.util.concurrent.TimeUnit

fun Long.elapsedMillis(currentTime: Long): Long = TimeUnit.NANOSECONDS.toMillis(currentTime - this)

data class RequestContext(val startTime: Long, val method: String, val path: String)
