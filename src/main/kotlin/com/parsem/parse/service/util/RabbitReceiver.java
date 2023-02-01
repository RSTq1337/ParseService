package com.parsem.parse.service.util;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class RabbitReceiver {
    private CountDownLatch latch = new CountDownLatch(1);

    @RabbitListener
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch = latch;
}
