package com.parsem.parse.service.serivce.api.onliner

import reactor.core.publisher.Mono

interface PhoneNumberService {
    fun getPhoneNumberInfo(userId: String): Mono<List<String>>
}