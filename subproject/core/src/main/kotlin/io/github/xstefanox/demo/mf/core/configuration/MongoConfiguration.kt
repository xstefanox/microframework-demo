package io.github.xstefanox.demo.mf.core.configuration

import com.typesafe.config.Config
import java.net.URL

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

    fun toURL() : URL {
        return URL("mongodb://$username:$password@$hostname:$port/$database")
    }
}
