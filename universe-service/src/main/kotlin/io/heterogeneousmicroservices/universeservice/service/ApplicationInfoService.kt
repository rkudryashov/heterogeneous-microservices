package io.heterogeneousmicroservices.universeservice.service

import io.heterogeneousmicroservices.universeservice.exception.UniverseServiceException
import io.heterogeneousmicroservices.universeservice.model.ApplicationInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ApplicationInfoService {

    @Value("\${spring.application.name}")
    private val applicationName: String? = null

    fun get(): ApplicationInfo = ApplicationInfo(applicationName
            ?: throw UniverseServiceException("Cannot get application name"))
}