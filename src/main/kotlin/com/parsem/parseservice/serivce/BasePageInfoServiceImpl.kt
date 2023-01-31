package com.parsem.parseservice.serivce

import org.springframework.web.reactive.function.client.WebClient;
import com.parsem.parseservice.config.ParseConfiguration
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BasePageInfoServiceImpl(
    private var config: ParseConfiguration,
    private var webClient: WebClient,
    private var onlinerApiTransferService: OnlinerApiTransferService
): BasePageInfoService {


    override fun getBasePageInfo() {
        var result = onlinerApiTransferService.transform(getGeneralInfo().block())
        var gf = "asdfdsf"
    }

    private fun getGeneralInfo(): Mono<String> {
            return webClient
                .get()
                .uri("search/vehicles?extended=true&limit=50")
                .retrieve()
                .bodyToMono(String::class.java)
    }


}