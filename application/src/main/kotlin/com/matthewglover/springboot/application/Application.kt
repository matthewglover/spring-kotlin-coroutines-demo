package com.matthewglover.springboot.application

import org.springframework.fu.kofu.reactiveWebApplication

val application = reactiveWebApplication {
    enable(webConfiguration)
    enable(dataConfiguration)
}

fun main() {
    application.run()
}
