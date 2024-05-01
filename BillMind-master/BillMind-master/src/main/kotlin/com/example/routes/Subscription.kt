package com.example.routes

import com.example.models.Subscription
import com.example.repositories.SubscriptionRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.subscriptionRouting() {
    route("/v1/subscriptions") {
        get {
            val subscriptions = SubscriptionRepository.getAllSubscriptions()
            call.respond(subscriptions)
        }

        get("/{subscriptionId}") {
            val subscriptionId = call.parameters["subscriptionId"]?.toIntOrNull()
            if (subscriptionId == null) {
                call.respond("Invalid subscription id")
                return@get
            }
            val subscription = SubscriptionRepository.getSubscriptionById(subscriptionId)
            if (subscription == null) {
                call.respond("Subscription not found")
            } else {
                call.respond(subscription)
            }
        }

        post {
            val subscription = call.receive<Subscription>()
            SubscriptionRepository.addSubscription(subscription)
            call.respond("Subscription created")
        }

        put("/{subscriptionId}") {
            val subscriptionId = call.parameters["subscriptionId"]?.toIntOrNull()
            if (subscriptionId == null) {
                call.respond("Invalid subscription id")
                return@put
            }
            val subscription = call.receive<Subscription>()
            if (subscription.id != subscriptionId) {
                call.respond("Invalid subscription id in body")
                return@put
            }
            SubscriptionRepository.updateSubscription(subscription)
            call.respond("Subscription updated")
        }
    }
}