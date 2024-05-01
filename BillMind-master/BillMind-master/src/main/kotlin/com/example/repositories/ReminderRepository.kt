package com.example.repositories

import com.example.models.Reminder
import com.example.repositories.ReminderRepository.Reminder.clientId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ReminderRepository {
    private object Reminder : Table() {
        val id = integer("id").autoIncrement()
        val clientId = integer("Client_id")
        val message = varchar("name", 100)
        val date = varchar("date", 100)
        val endReminder = varchar("end_reminder", 100)
    }

    private fun resultRowToReminder(row: ResultRow): com.example.models.Reminder {
        return Reminder(
            id = row[Reminder.id],
            idClient = row[clientId],
            message = row[Reminder.message],
            date = row[Reminder.date],
            endReminder = row[Reminder.endReminder]
        )
    }

    // Funcion que obtiene todos los recordatorios
    fun getAllReminders(): List<com.example.models.Reminder> {
        return transaction {
            Reminder.selectAll().map { resultRowToReminder(it) }
        }
    }

    // Funcion que obtiene un recordatorio por su id
    fun getReminderById(reminderId: Int): com.example.models.Reminder? {
        return transaction {
            Reminder.select { Reminder.id eq reminderId }.map { resultRowToReminder(it) }.firstOrNull()
        }
    }

    // Funcion que agrega un recordatorio
    fun addReminder(reminder: com.example.models.Reminder) {
        transaction {
            ReminderRepository.Reminder.insert {
                it[clientId] = reminder.idClient
                it[message] = reminder.message
                it[date] = reminder.date
                it[endReminder] = reminder.endReminder
            }
        }
    }

    // Funcion que actualiza un recordatorio
    fun updateReminder(reminder: com.example.models.Reminder) {
        transaction {
            ReminderRepository.Reminder.update({ ReminderRepository.Reminder.id eq reminder.id }) {
                it[clientId] = reminder.idClient
                it[message] = reminder.message
                it[date] = reminder.date
                it[endReminder] = reminder.endReminder
            }
        }
    }

    // Funcion que obtiene todos los recordatorios de un cliente
    fun getReminderByClientId(clientId: Int): List<com.example.models.Reminder> {
        return transaction {
            Reminder.select { Reminder.clientId eq clientId }.map { resultRowToReminder(it) }
        }
    }
}