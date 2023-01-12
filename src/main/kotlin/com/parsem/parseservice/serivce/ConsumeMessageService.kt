package com.parsem.parseservice.serivce

interface ConsumeMessageService {
    fun consumeMessage(messageBody: String)
}