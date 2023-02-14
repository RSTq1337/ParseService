package com.parsem.parse.service.serivce.api.onliner

import org.springframework.web.reactive.function.client.WebClient;
import com.parsem.parse.service.serivce.OnlinerApiTransferService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BasePageInfoServiceImpl(
    private var webClient: WebClient,
    private var onlinerApiTransferService: OnlinerApiTransferService
): BasePageInfoService {


    override fun getBasePageInfo() {
        onlinerApiTransferService.transform(getGeneralInfo())
        print("asd")
    }

    fun getGeneralInfo(): Mono<String> {
            return webClient
                .get()
                .uri("search/vehicles?extended=true&limit=50")
                .retrieve()
                .bodyToMono(String::class.java)
    }
}