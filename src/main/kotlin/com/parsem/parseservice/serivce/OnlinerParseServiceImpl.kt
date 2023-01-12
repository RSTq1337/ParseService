package com.parsem.parseservice.serivce

import com.parsem.parseservice.config.ParseConfiguration
import org.springframework.stereotype.Service

@Service
class OnlinerParseServiceImpl(
    private val parseConfiguration: ParseConfiguration
): OnlinerParseService {

    override fun parseOnliner(carRequest: String) {

    }
}