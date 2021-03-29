package com.matthewglover.springboot.application.context

import kotlinx.collections.immutable.PersistentMap

data class DefaultTags(private val map: PersistentMap<String, String>) :
    PersistentMap<String, String> by map

fun DefaultTags.withElapsedMillis(elapsedMillis: Long): DefaultTags =
    DefaultTags(put("elapsedMillis", elapsedMillis.toString()))
