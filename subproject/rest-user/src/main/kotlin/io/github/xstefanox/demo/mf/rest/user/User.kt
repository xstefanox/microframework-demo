package io.github.xstefanox.demo.mf.rest.user

import io.github.xstefanox.demo.mf.core.Id

data class User(
    val id: Id,
    val firstName: String,
    val lastName: String
)
