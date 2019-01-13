package io.heterogeneousmicroservices.ktorservice

import com.orbitz.consul.Consul
import io.heterogeneousmicroservices.ktorservice.config.ApplicationInfoProperties
import io.heterogeneousmicroservices.ktorservice.service.ApplicationInfoService
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin

val applicationContext = module {
    single { ApplicationInfoService(get(), get()) }
    single { ApplicationInfoProperties() }
    single { Consul.builder().withUrl("http://localhost:8500").build() }
}

fun main(args: Array<String>) {
    startKoin(listOf(applicationContext))
    val server = embeddedServer(Netty, commandLineEnvironment(args))
    server.start(wait = true)
}