package com.matthewglover.springboot.application

import com.matthewglover.springboot.application.handlerfilters.CreateRequestContextFilter
import com.matthewglover.springboot.application.handlerfilters.RequestResponseLoggerFilter
import com.matthewglover.springboot.application.users.UserHandlers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.web.reactive.function.server.coRouter

@ExperimentalCoroutinesApi
fun userRoutes(userHandlers: UserHandlers, createRequestContextFilter: CreateRequestContextFilter, requestResponseLoggerFilter: RequestResponseLoggerFilter) = coRouter {
    DELETE("/user/{userId}", userHandlers::delete)
    GET("/users", userHandlers::all)
    GET("/user/{userId}", userHandlers::get)
    POST("/user", userHandlers::add)
    PUT("/user", userHandlers::update)

    filter(createRequestContextFilter::apply)
    filter(CreateDefaultTagsFilter::apply)
    filter(requestResponseLoggerFilter::apply)
}
