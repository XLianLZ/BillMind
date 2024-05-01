package com.example.routes

import com.example.models.Card
import com.example.repositories.CardRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.cardRouting() {
    route("/v1/cards") {
        get {
            val cards = CardRepository.getAllCards()
            call.respond(cards)
        }

        get("/{cardId}") {
            val cardId = call.parameters["cardId"]?.toIntOrNull()
            if (cardId == null) {
                call.respond("Invalid card id")
                return@get
            }
            val card = CardRepository.getCardById(cardId)
            if (card == null) {
                call.respond("Card not found")
            } else {
                call.respond(card)
            }
        }

        post {
            val card = call.receive<Card>()
            CardRepository.addCard(card)
            call.respond("Card added")
        }

        put("/{cardId}") {
            val cardId = call.parameters["cardId"]?.toIntOrNull()
            if (cardId == null) {
                call.respond("Invalid card id")
                return@put
            }
            val card = call.receive<Card>()
            if (card.id != cardId) {
                call.respond("Invalid card id in body")
                return@put
            }
            CardRepository.updateCard(card)
            call.respond("Card updated")
        }
    }
}