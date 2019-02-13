package io.github.xstefanox.demo.mf.core.configuration

import com.typesafe.config.Config

data class MySqlConfiguration(
    val user: String,
    val password: String,
    val host: String,
    val port: Int,
    val schema: String
) {
    constructor(config: Config) : this(
        user = config.getString("user"),
        password = config.getString("password"),
        host = config.getString("host"),
        port = config.getInt("port"),
        schema = config.getString("schema")
    )
}
