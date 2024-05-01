package com.example.repositories

import com.example.models.Client
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ClientRepository {
    private object Client : Table() {
        val id = integer("id").autoIncrement()
        val name = varchar("name", 100)
        val lastName = varchar("last_name", 100)
        val mail = varchar("mail", 100)
        val phone = varchar("phone", 100)
    }

    private fun resultRowToClient(row: ResultRow): com.example.models.Client {
        return Client(
            id = row[Client.id],
            name = row[Client.name],
            lastName = row[Client.lastName],
            mail = row[Client.mail],
            phone = row[Client.phone]
        )
    }

    // Funcion para obtener todos los clientes
    fun getAllClients(): List<com.example.models.Client> {
        return transaction {
            Client.selectAll().map { resultRowToClient(it) }
        }
    }

    // Funcion para obtener un cliente por su id
    fun getClientById(clientId: Int): com.example.models.Client? {
        return transaction {
            Client.select { Client.id eq clientId }.map { resultRowToClient(it) }.firstOrNull()
        }
    }

    // Funcion para agregar un cliente
    fun addClient(client: com.example.models.Client) {
        transaction {
            ClientRepository.Client.insert {
                it[name] = client.name
                it[lastName] = client.lastName
                it[mail] = client.mail
                it[phone] = client.phone
            }
        }
    }

    // Funcion para actualizar un cliente
    fun updateClient(client: com.example.models.Client) {
        transaction {
            ClientRepository.Client.update({ ClientRepository.Client.id eq client.id }) {
                it[name] = client.name
                it[lastName] = client.lastName
                it[mail] = client.mail
                it[phone] = client.phone
            }
        }
    }

    // Funcion para eliminar un cliente por su id
    /*fun deleteClientById(clientId: Int) {
        transaction {
            Clients.deleteWhere { Clients.id eq clientId }
        }
    }*/
}