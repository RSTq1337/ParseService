package com.parsem.parseservice.serivce

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class PhoneNumberServiceImpl(
    private var webClient: WebClient
): PhoneNumberService {

    override fun getPhoneNumber(userId: String): String? {
        var listOfPhone = getPhoneNumberInfo(userId).block();
        return if (listOfPhone is List<String>) {
            listOfPhone[0]
        } else null
    }

    private fun getPhoneNumberInfo(userId: String): Mono<List<String>> {
        var answer : Mono<List<String>> = Mono.empty();
        try {
             answer = webClient
                .get()
                .uri("vehicles/$userId/phones")
                .retrieve()
                .onStatus(
                    { other: HttpStatusCode? ->
                        HttpStatus.NOT_FOUND == other
                    }
                ) { response: ClientResponse ->
                    response.bodyToMono(
                        String::class.java
                    )
                        .map { message: String? ->
                            Exception(
                                message
                            )
                        }
                }
                .bodyToMono(object : ParameterizedTypeReference<List<String>>() {})
        } catch (ex: Exception) {
            if (ex.message.equals("{\"message\":\"Not Found\"}"))
                return Mono.empty()
        }
        return answer
    }

}