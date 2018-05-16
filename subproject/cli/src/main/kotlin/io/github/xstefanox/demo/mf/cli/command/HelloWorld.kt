package io.github.xstefanox.demo.mf.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi.echo

class HelloWorld : CliktCommand(name = "hello:world") {

    override fun run() {
        echo("Hello world!")
    }
}