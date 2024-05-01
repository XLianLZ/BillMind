package com.example.routes

import com.example.models.Client
import com.example.repositories.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.clientRouting() {
    route("/v1/clients") {

        // GET /v1/clients
        get {
            val clients = ClientRepository.getAllClients()
            call.respond(clients)
        }

        // GET /v1/clients/{clientId}
        get("/{clientId}") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id")
                return@get
            }
            val client = ClientRepository.getClientById(clientId)
            if (client == null) {
                call.respond(HttpStatusCode.NotFound, "Client not found")
            } else {
                call.respond(client)
            }
        }

        // POST /v1/clients
        post {
            val client = call.receive<Client>()
            ClientRepository.addClient(client)
            call.respond(HttpStatusCode.Created)
        }

        // PUT /v1/clients/{clientId}
        put("/{clientId}") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id")
                return@put
            }
            val client = call.receive<Client>()
            if (client.id != clientId) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id in body")
                return@put
            }
            ClientRepository.updateClient(client)
            call.respond(HttpStatusCode.OK)
        }

        // DELETE /v1/clients/{clientId}
        /*
        delete("/{clientId}") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id")
                return@delete
            }
            ClientRepository.deleteClient(clientId)
            call.respond(HttpStatusCode.OK)
        }
        */

        // GET /v1/clients/{clientId}/debts
        get("/{clientId}/debts") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id")
                return@get
            }
            val debts = DebtRepository.getDebtsByClientId(clientId)
            call.respond(debts)
        }

        // GET /v1/clients/{clientId}/reminders
        get("/{clientId}/reminders") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id")
                return@get
            }
            val reminders = ReminderRepository.getReminderByClientId(clientId)
            call.respond(reminders)
        }

        // GET /v1/clients/{clientId}/cards
        get("/{clientId}/cards") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id")
                return@get
            }
            val cards = CardRepository.getCardByClientId(clientId)
            call.respond(cards)
        }

        // GET /v1/clients/{clientId}/subscriptions
        get("/{clientId}/subscriptions") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client id")
                return@get
            }
            val subscriptions = SubscriptionRepository.getSubscriptionByClientId(clientId)
            call.respond(subscriptions)
        }
    }
}