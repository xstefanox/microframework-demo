@file:JvmName("Kodein")

package io.github.xstefanox.demo.mf.core

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files

val CORE_MODULE = Kodein.Module {

    bind<FileSystem>() with singleton { FileSystems.getDefault() }

    bind<Config>() with singleton {

        val fileSystem = instance<FileSystem>()
        val configFilePath = fileSystem.getPath(System.getProperty("user.dir"), "application.conf")

        if (Files.exists(configFilePath)) {
            System.setProperty("config.file", "application.conf")
        }

        ConfigFactory.load()
    }
}
