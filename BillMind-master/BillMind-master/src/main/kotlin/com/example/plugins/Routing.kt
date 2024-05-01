package com.example.plugins

import com.example.routes.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Aca no hay nada, ve a /v1/clients o /v1/debts, etc")
        }
        clientRouting()
        debtRouting()
    }
}