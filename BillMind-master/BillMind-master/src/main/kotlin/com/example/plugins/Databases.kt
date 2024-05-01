package com.example.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import java.io.File

fun loadEnvironmentVariables(): Map<String, String> {
    val file = File("src/main/kotlin/com/example/environments.env")
    if (!file.exists()) {
        throw IllegalArgumentException("The .env file does not exist")
    }
    return file.readLines().map { it.split("=") }.associate { it[0] to it[1] }
}

fun Application.configureDatabases() {
    val env = loadEnvironmentVariables()
    val database = env["MYSQLUSER"]?.let {
        env["MYSQLPASSWORD"]?.let { it1 ->
            Database.connect(
            /*
            url = "jdbc:mysql://localhost:3306/billmind",
            user = "Noru",
            password = "E_noru1108",
            driver = "org.h2.Driver"
            */

            /*
            url = "jdbc:mysql://root:mXRmlfEOTzJdcMEozpWxjIwccvQnUTnh@roundhouse.proxy.rlwy.net:40763/railway",
            user = "root",
            password = "mXRmlfEOTzJdcMEozpWxjIwccvQnUTnh",
            driver = "org.h2.Driver"
             */

            /*
            url = "jdbc:mysql://bad3v4dubu0xltichxug-mysql.services.clever-cloud.com:3306/bad3v4dubu0xltichxug",
            user = "uxi7z0hyd8mttwlr",
            password = "plu7JXsFU0TPztOUTSyq",
            driver = "org.h2.Driver"
            */

            url = "jdbc:mysql://${env["MYSQLHOST"]}:${env["MYSQLPORT"]}/${env["MYSQLDATABASE"]}",
            user = it,
            password = it1,
            driver = "com.mysql.cj.jdbc.Driver"
        )
        }
    }
}