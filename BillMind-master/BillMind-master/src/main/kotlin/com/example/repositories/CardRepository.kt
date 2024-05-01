package com.example.repositories

import com.example.models.Card
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object CardRepository {
    private object Card : Table() {
        val id = integer("id").autoIncrement()
        val clientId = integer("Client_id")
        val cardName = varchar("card_name", 100)
        val cardNumber = varchar("card_number", 100)
        val expirationDate = varchar("expiration_date", 100)
    }

    private fun resultRowToCard(row: ResultRow): com.example.models.Card {
        return Card(
            id = row[Card.id],
            idClient = row[Card.clientId],
            cardName = row[Card.cardName],
            cardNumber = row[Card.cardNumber],
            expirationDate = row[Card.expirationDate]
        )
    }

    // Funcion que obtiene todas las tarjetas
    fun getAllCards(): List<com.example.models.Card> {
        return transaction {
            Card.selectAll().map { resultRowToCard(it) }
        }
    }

    // Funcion que obtiene una tarjeta por su id
    fun getCardById(cardId: Int): com.example.models.Card? {
        return transaction {
            Card.select { Card.id eq cardId }.map { resultRowToCard(it) }.firstOrNull()
        }
    }

    // Funcion que agrega una tarjeta
    fun addCard(card: com.example.models.Card) {
        transaction {
            CardRepository.Card.insert {
                it[clientId] = card.idClient
                it[cardName] = card.cardName
                it[cardNumber] = card.cardNumber
                it[expirationDate] = card.expirationDate
            }
        }
    }

    // Funcion que actualiza una tarjeta
    fun updateCard(card: com.example.models.Card) {
        transaction {
            CardRepository.Card.update({ CardRepository.Card.id eq card.id }) {
                it[clientId] = card.idClient
                it[cardName] = card.cardName
                it[cardNumber] = card.cardNumber
                it[expirationDate] = card.expirationDate
            }
        }
    }

    // Funcion que obtiene todas las tarjetas de un cliente
    fun getCardByClientId(clientId: Int): List<com.example.models.Card> {
        return transaction {
            Card.select { Card.clientId eq clientId }.map { resultRowToCard(it) }
        }
    }
}