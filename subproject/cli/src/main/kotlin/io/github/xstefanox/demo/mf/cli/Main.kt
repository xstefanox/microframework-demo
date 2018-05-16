@file:JvmName("Main")

package io.github.xstefanox.demo.mf.cli

import com.github.ajalt.clikt.core.CliktCommand
import org.kodein.di.generic.instance

fun main(args: Array<String>) {

    val command by CLI_KODEIN.instance<CliktCommand>()

    command.main(args)
}
