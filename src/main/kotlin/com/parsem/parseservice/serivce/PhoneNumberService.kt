package com.parsem.parseservice.serivce

interface PhoneNumberService {
    fun getPhoneNumber(userId: String): String?
}