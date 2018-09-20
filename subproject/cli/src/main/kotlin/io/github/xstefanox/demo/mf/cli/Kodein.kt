@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import io.github.xstefanox.demo.mf.cli.command.HelloWorld
import io.github.xstefanox.demo.mf.cli.command.ParentCommand
import io.github.xstefanox.demo.mf.core.CORE_MODULE
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val CLI_MODULE = Kodein.Module("CLI") {
    bind<CliktCommand>() with singleton {
        ParentCommand()
                .subcommands(HelloWorld())
    }
}

val CLI_KODEIN = Kodein {
    import(CORE_MODULE)
    import(CLI_MODULE)
}
