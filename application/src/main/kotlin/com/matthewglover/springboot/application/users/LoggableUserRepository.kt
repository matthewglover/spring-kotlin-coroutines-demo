package com.matthewglover.springboot.application.users

import com.matthewglover.springboot.application.User
import com.matthewglover.springboot.application.logging.ContextLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.apache.logging.log4j.message.StringMapMessage

class LoggableUserRepository(val delegate: UserRepository) : UserRepository by delegate {

    private val logger = ContextLogger(UserRepository::class.java)

    @ExperimentalCoroutinesApi
    override suspend fun all(): List<User> {
        logger.info { defaultTags, _ ->
            StringMapMessage(
                defaultTags.putAll(
                    mapOf<String, String>(
                        "command" to "userRepository.all",
                        "message" to "starting request"
                    )
                )
            )
        }

        val result = delegate.all()

        logger.info { defaultTags, _ ->
            StringMapMessage(
                defaultTags.putAll(
                    mapOf(
                        "command" to "userRepository.all",
                        "message" to "request completed",
                        "result" to result.toString()
                    )
                )
            )
        }

        return result
    }
}
