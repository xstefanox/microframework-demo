package io.github.xstefanox.demo.mf.core.configuration

import com.typesafe.config.Config
import java.net.URI

data class MySqlConfiguration(
    val username: String,
    val password: String,
    val hostname: String,
    val port: Int,
    val schema: String
) {
    constructor(config: Config) : this(
        username = config.getString("username"),
        password = config.getString("password"),
        hostname = config.getString("hostname"),
        port = config.getInt("port"),
        schema = config.getString("schema")
    )

    fun toURI() : URI {
        return URI("jdbc:mysql://$username:$password@$hostname:$port/$schema")
    }
}
