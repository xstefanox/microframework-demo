package io.github.xstefanox.demo.mf.core.configuration

import com.typesafe.config.Config
import java.net.URI

data class MongoConfiguration(
    val username: String,
    val password: String,
    val hostname: String,
    val port: Int,
    val database: String
) {
    constructor(config: Config) : this(
        username = config.getString("username"),
        password = config.getString("password"),
        hostname = config.getString("hostname"),
        port = config.getInt("port"),
        database = config.getString("database")
    )

    fun toURI() : URI {
        return URI("mongodb://$username:$password@$hostname:$port/$database")
    }
}
