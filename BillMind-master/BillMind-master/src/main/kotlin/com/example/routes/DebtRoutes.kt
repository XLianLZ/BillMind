package com.example.routes

import com.example.models.Debt
import com.example.repositories.DebtRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.text.get

fun Routing.debtRouting() {
    route("/v1/debts") {
        get {
            val debts = DebtRepository.getAllDebts()
            call.respond(debts)
        }

        get("/{debtId}") {
            val debtId = call.parameters["debtId"]?.toIntOrNull()
            if (debtId == null) {
                call.respond("Invalid debt id")
                return@get
            }
            val debt = DebtRepository.getDebtById(debtId)
            if (debt == null) {
                call.respond("Debt not found")
            } else {
                call.respond(debt)
            }
        }

        post {
            val debt = call.receive<Debt>()
            DebtRepository.addDebt(debt)
            call.respond("Debt created")
        }

        put("/{debtId}") {
            val debtId = call.parameters["debtId"]?.toIntOrNull()
            if (debtId == null) {
                call.respond("Invalid debt id")
                return@put
            }
            val debt = call.receive<Debt>()
            if (debt.id != debtId) {
                call.respond("Invalid debt id in body")
                return@put
            }
            DebtRepository.updateDebt(debt)
            call.respond("Debt updated")
        }
    }
}