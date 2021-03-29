package com.matthewglover.springboot.application

import com.matthewglover.springboot.application.handlerfilters.CreateRequestContextFilter
import com.matthewglover.springboot.application.handlerfilters.RequestResponseLoggerFilter
import com.matthewglover.springboot.application.users.LoggableUserRepository
import com.matthewglover.springboot.application.users.UserHandlers
import com.matthewglover.springboot.application.users.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.boot.logging.LogLevel
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.dataR2dbc
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.core.DatabaseClient

@ExperimentalCoroutinesApi
val webConfiguration = configuration {
    logging { level = LogLevel.INFO }
    beans {
        bean<CreateRequestContextFilter>()
        bean<RequestResponseLoggerFilter>()
        bean<UserRepository>() {
            val dbUserRepository = DbUserRepository(ref<DatabaseClient>())
            LoggableUserRepository(dbUserRepository)
        }
        bean<UserHandlers>()
        bean(::userRoutes)
    }
    webFlux {
        port = 8080
        codecs {
            string()
            jackson()
        }
    }
}

val dataConfiguration = configuration {
    beans {
        bean {
            ConnectionFactoryInitializer().apply {
                setConnectionFactory(ref())
            }
        }
    }
    dataR2dbc {
        r2dbc {
            url = "r2dbc:postgresql://localhost:5433/devdb"
            username = "devdb"
            password = "devpassword"
        }
    }
}
