@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.async.client.MongoDatabase
import com.rabbitmq.client.ConnectionFactory
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.github.xstefanox.demo.mf.core.configuration.MongoConfiguration
import io.github.xstefanox.demo.mf.core.configuration.MySqlConfiguration
import io.github.xstefanox.demo.mf.core.configuration.RabbitMqConfiguration
import io.github.xstefanox.demo.mf.core.purchase.PurchaseManager
import io.github.xstefanox.demo.mf.core.purchase.Purchases
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.litote.kmongo.async.KMongo
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files

val CORE_MODULE = Kodein.Module("CORE") {

    val config = loadConfiguration("application")

    bind<FileSystem>() with singleton { FileSystems.getDefault() }

    bind<Config>() with singleton {

        val fileSystem = instance<FileSystem>()
        val configFilePath = fileSystem.getPath(System.getProperty("user.dir"), "application.conf")

        if (Files.exists(configFilePath)) {
            System.setProperty("config.file", "application.conf")
        }

        ConfigFactory.load()
    }

    bind<MySqlConfiguration>() with singleton {
        MySqlConfiguration(config.getConfig("mysql"))
    }

    bind<RabbitMqConfiguration>() with singleton {
        RabbitMqConfiguration(config.getConfig("rabbitmq"))
    }

    bind<MongoConfiguration>() with singleton {
        MongoConfiguration(config.getConfig("mongodb"))
    }

    bind<ObjectMapper>() with singleton { ObjectMapper() }

    bind<MongoDatabase>() with singleton {

        val configuration = instance<MongoConfiguration>()

        KMongo.createClient(configuration.toURI().toString())
            .getDatabase(configuration.database)
    }

    bind<Database>() with singleton {

        val configuration = instance<MySqlConfiguration>()
        val database = Database.connect(configuration.toURI().toString(), driver = "com.mysql.cj.jdbc.Driver")

        transaction(database) {
            SchemaUtils.create(Purchases)
        }

        database
    }

    bind<ConnectionFactory>() with singleton {

        val rabbitmq = instance<RabbitMqConfiguration>()

        val connectionFactory = ConnectionFactory()
        connectionFactory.username = rabbitmq.username
        connectionFactory.password = rabbitmq.password
        connectionFactory.host = rabbitmq.hostname
        connectionFactory.port = rabbitmq.port
        connectionFactory.virtualHost = rabbitmq.vhost

        connectionFactory
    }

    bind<PurchaseManager>() with singleton { PurchaseManager(instance(), instance(), instance()) }
}
