package io.heterogeneousmicroservices.springbootservice.service

import io.heterogeneousmicroservices.springbootservice.model.ApplicationInfo
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ServiceClient(
    private val restTemplate: RestTemplate
) {

    fun getApplicationInfo(serviceName: String): ApplicationInfo = restTemplate
        .getForEntity("http://$serviceName/application-info", ApplicationInfo::class.java)
        .body ?: throw IllegalStateException("No result")
}