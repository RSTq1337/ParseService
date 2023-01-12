package com.parsem.parseservice.serivce

import com.google.gson.Gson
import com.parsem.parseservice.dto.CarSpecsRequestQueue
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
        var myObject = Gson().fromJson(messageBody, CarSpecsRequestQueue::class.java)
        log.info("//////////////////////////////")
    }
    private companion object {
        val log: org.apache.logging.log4j.Logger = LogManager.getLogger()
    }
}