package com.example.repositories

import com.example.models.Subscription
import com.example.repositories.SubscriptionRepository.Subscription.clientId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object SubscriptionRepository {
    private object Subscription : Table() {
        val id = integer("id").autoIncrement()
        val clientId = integer("Client_id")
        val type = varchar("type", 100)
        val initialDate =  varchar("initial_date", 100)
        val endDate = varchar("end_date", 100)
    }

    private fun resultRowToSubscription(row: ResultRow): com.example.models.Subscription {
        return Subscription(
            id = row[Subscription.id],
            idClient = row[clientId],
            type = row[Subscription.type],
            initialDate = row[Subscription.initialDate],
            endDate = row[Subscription.endDate]
        )
    }

    // Funcion que obtiene todas las suscripciones
    fun getAllSubscriptions(): List<com.example.models.Subscription> {
        return transaction {
            Subscription.selectAll().map { resultRowToSubscription(it) }
        }
    }

    // Funcion que obtiene una suscripcion por su id
    fun getSubscriptionById(subscriptionId: Int): com.example.models.Subscription? {
        return transaction {
            Subscription.select { Subscription.id eq subscriptionId }.map { resultRowToSubscription(it) }.firstOrNull()
        }
    }

    // Funcion que agrega una suscripcion
    fun addSubscription(subscription: com.example.models.Subscription) {
        transaction {
            SubscriptionRepository.Subscription.insert {
                it[clientId] = subscription.idClient
                it[type] = subscription.type
                it[initialDate] = subscription.initialDate
                it[endDate] = subscription.endDate
            }
        }
    }

    // Funcion que actualiza una suscripcion
    fun updateSubscription(subscription: com.example.models.Subscription) {
        transaction {
            SubscriptionRepository.Subscription.update({ SubscriptionRepository.Subscription.id eq subscription.id }) {
                it[clientId] = subscription.idClient
                it[type] = subscription.type
                it[initialDate] = subscription.initialDate
                it[endDate] = subscription.endDate
            }
        }
    }

    // Funcion que obtiene la suscripcion de un cliente
    fun getSubscriptionByClientId(clientId: Int): List<com.example.models.Subscription> {
        return transaction {
            Subscription.select { Subscription.clientId eq clientId }.map { resultRowToSubscription(it) }
        }
    }
}