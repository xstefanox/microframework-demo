package io.github.xstefanox.demo.mf.cli.command

import com.github.ajalt.clikt.core.CliktCommand

class HelloWorld : CliktCommand(name = "hello:world") {

    override fun run() {
        echo("Hello world!")
    }
}