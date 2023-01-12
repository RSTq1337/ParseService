package com.parsem.parseservice.serivce

import org.springframework.web.reactive.function.client.WebClient;
import com.parsem.parseservice.config.ParseConfiguration
import com.parsem.parseservice.dto.DataOnliner
import com.parsem.parseservice.util.OnlinerApiTransfer
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.PrivateKey

@Service
class BasePageInfoServiceImpl(
    private var config: ParseConfiguration,
    private var webClient: WebClient
): BasePageInfoService {


    override fun getBasePageInfo() {

        var data = getData().block()?.let { OnlinerApiTransfer.transform(it) }

    }

        fun getData(): Mono<String> {
            return webClient
                .get()
                .retrieve()
                .bodyToMono(String::class.java)
        }
}