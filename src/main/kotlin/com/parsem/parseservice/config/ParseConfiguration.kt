package com.parsem.parseservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ParseConfiguration {

    @Value("\${parse.base-path.onliner}")
    val basePathOnliner: String? = null
}