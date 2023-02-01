package com.parsem.parse.service.serivce

import com.google.gson.Gson
import com.parsem.parse.service.dto.CarSpecsRequestQueue
import com.parsem.parse.service.serivce.api.onliner.BasePageInfoService
import org.apache.logging.log4j.LogManager
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@EnableRabbit
class ConsumeMessageServiceImpl(
    var basePageInfoService: BasePageInfoService
): ConsumeMessageService {

    @RabbitListener(queues = ["carQueue"])
    override fun consumeMessage(messageBody: String) {
        log.info("Consumed Message: $messageBody")
        Gson().fromJson(messageBody, CarSpecsRequestQueue::class.java)
        log.info("//////////////////////////////")
    }
    private companion object {
        val log: org.apache.logging.log4j.Logger = LogManager.getLogger()
    }
}