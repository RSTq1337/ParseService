package com.parsem.parseservice.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.util.concurrent.TimeUnit


@Configuration
class WebClientOnlinerConfiguration {
    private val BASE_URL = "https://ab.onliner.by/sdapi/ab.api/"
    val TIMEOUT = 1000

    @Bean
    fun webClient(): WebClient? {
        val size = 16 * 1024 * 1024
        val strategies = ExchangeStrategies.builder()
            .codecs { codecs: ClientCodecConfigurer ->
                codecs.defaultCodecs().maxInMemorySize(size)
            }
            .build()
        val tcpClient = TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .doOnConnected { connection: Connection ->
                connection.addHandlerLast(ReadTimeoutHandler(TIMEOUT.toLong(), TimeUnit.MILLISECONDS))
                connection.addHandlerLast(WriteTimeoutHandler(TIMEOUT.toLong(), TimeUnit.MILLISECONDS))
            }
        return WebClient.builder()
            .baseUrl(BASE_URL)
            .clientConnector(ReactorClientHttpConnector(HttpClient.from(tcpClient)))
            .exchangeStrategies(strategies)
            .build()
    }
}