package io.heterogeneousmicroservices.paralleluniverseservice.service

import io.heterogeneousmicroservices.paralleluniverseservice.model.ApplicationInfo
import io.micronaut.context.annotation.Value
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class ApplicationInfoService(
        @Value("\${micronaut.application.name}") private val applicationName: String
) {

    fun get(): Single<ApplicationInfo> = Single.just(ApplicationInfo(applicationName))
}