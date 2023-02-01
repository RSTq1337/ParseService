package com.parsem.parse.service.serivce

interface ConsumeMessageService {
    fun consumeMessage(messageBody: String)
}