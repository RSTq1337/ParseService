package com.parsem.parse.service.serivce.api.onliner.search

import com.parsem.parse.service.util.RequestHeadersUtil
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SearchCarByFilterService (
    private var webClient: WebClient
){

    fun getCarsByBrand(brand: String): Mono<String> {
        val carId = RequestHeadersUtil.mapOfBrands[brand]
        return webClient
            .get()
            .uri("search/vehicles?car[0][manufacturer]=$carId&extended=true&limit=50")
            .retrieve()
            .bodyToMono(String::class.java)
    }

}