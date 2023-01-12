package com.parsem.parseservice.dto

data class CarDataFromOnliner(
    var title: String = "",
    var authorId: String = "",
    var brand: String = "",
    var model: String = "",
    var generation: String = "",
    var transmission: String = "",
    var odometer: String = "",
    var year: String = "",
    var color: String = "",
    var bodyType: String = "",
    var state: String = "",
    var driveTrain: String = "",
    var modification: String = "",
    var engineType: String = "",
    var engineCapacity: String = "",
    var engineTorque: String = "",
    var enginePower: String = "",
    var sellerNumber: String = "",
    var priceUSD: String = "",
    var priceBYN: String = "",
    var imagesOG: Set<String> = emptySet(),
    var images1900: Set<String> = emptySet(),
    var images800: Set<String> = emptySet(),
    var images600: Set<String> = emptySet(),
    var images380: Set<String> = emptySet(),
    var images100: Set<String> = emptySet(),
    var images80: Set<String> = emptySet(),
    var created: String = "",
    var url: String = ""

): Dto
