package com.example.routes

import com.example.models.Reminder
import com.example.repositories.ReminderRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.reminderRouting() {
    route("/reminder") {
        get {
            val reminders = ReminderRepository.getAllReminders()
            call.respond(reminders)
        }

        get("/{reminderId}") {
            val reminderId = call.parameters["reminderId"]?.toIntOrNull()
            if (reminderId == null) {
                call.respond("Invalid reminder id")
                return@get
            }
            val reminder = ReminderRepository.getReminderById(reminderId)
            if (reminder == null) {
                call.respond("Reminder not found")
            } else {
                call.respond(reminder)
            }
        }

        post {
            val reminder = call.receive<Reminder>()
            ReminderRepository.addReminder(reminder)
            call.respond("Reminder added")
        }

        put("/{reminderId}") {
            val reminderId = call.parameters["reminderId"]?.toIntOrNull()
            if (reminderId == null) {
                call.respond("Invalid reminder id")
                return@put
            }
            val reminder = call.receive<Reminder>()
            if (reminder.id != reminderId) {
                call.respond("Invalid reminder id in body")
                return@put
            }
            ReminderRepository.updateReminder(reminder)
            call.respond("Reminder updated")
        }
    }
}