package com.parsem.parse.service.serivce

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.parsem.parse.service.dto.CarDataFromOnliner
import com.parsem.parse.service.serivce.api.onliner.PhoneNumberService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OnlinerApiTransferService (
    private var phoneNumberService: PhoneNumberService
){

    fun transform(jsonMono: Mono<String>): Set<CarDataFromOnliner> {
        val normalJson = JsonParser.parseString(jsonMono.block()).asJsonObject.get("adverts").asJsonArray
        return normalJson.map {
            car -> CarDataFromOnliner(
            title = car.asJsonObject.get("title").asString,
            authorId = car.asJsonObject.get("author").asJsonObject.get("id").asString,
            brand = car.asJsonObject.get("manufacturer").asJsonObject.get("name").asString,
            model = car.asJsonObject.get("model").asJsonObject.get("name").asString,
            generation = car.asJsonObject.get("generation").asJsonObject.get("name").asString,
            transmission = car.asJsonObject.get("specs").asJsonObject.get("transmission").asString,
            odometer = car.asJsonObject.get("specs").asJsonObject.get("odometer").asJsonObject.get("unit").asString,
            year = car.asJsonObject.get("specs").asJsonObject.get("year").asString,
            color = car.asJsonObject.get("specs").asJsonObject.get("color").asString,
            bodyType = car.asJsonObject.get("specs").asJsonObject.get("body_type").asString,
            state = car.asJsonObject.get("specs").asJsonObject.get("state").asString,
            driveTrain = car.asJsonObject.get("specs").asJsonObject.get("drivetrain").asString,
            modification = when(
             car.asJsonObject.get("specs").asJsonObject.get("modification").isJsonNull
            ){
                true -> null
                false ->  car.asJsonObject.get("specs").asJsonObject.get("modification").asString
            } ,
            engineType = when(
             car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("type").isJsonNull
            ){
                true -> null
                false ->  car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("type").asString
            },
            engineCapacity = when(
             car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("capacity").isJsonNull
            ){
                true -> null
                false ->  car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("capacity").asString
            },
            engineTorque = when(
             car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("torque").isJsonNull
            ){
                true -> null
                false ->  car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("torque").asString
            },
            enginePower = when(
             car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("power").isJsonNull
            ){
                true -> null
                false ->  car.asJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("power").asJsonObject.get("value").asString
            },
            sellerNumber = fillingPhoneNumber( car.asJsonObject.get("author").asJsonObject.get("id").asString).orEmpty(),
            priceUSD =  car.asJsonObject.get("price").asJsonObject.get("converted").asJsonObject.get("USD").asJsonObject.get("amount").asString,
            priceBYN =   car.asJsonObject.get("price").asJsonObject.get("converted").asJsonObject.get("BYN").asJsonObject.get("amount").asString,
            imagesOG = creatingListOfImages( car.asJsonObject, "OG"),
            images1900 = creatingListOfImages( car.asJsonObject, "1900"),
            images800 = creatingListOfImages( car.asJsonObject, "800"),
            images600 = creatingListOfImages( car.asJsonObject, "600"),
            images380 = creatingListOfImages( car.asJsonObject, "380"),
            images100 = creatingListOfImages( car.asJsonObject, "100"),
            images80 = creatingListOfImages( car.asJsonObject, "80"),
            created =  car.asJsonObject.get("created_at").asString,
            url =  car.asJsonObject.get("url").asString
            )}.toSet()
        }
    fun fillingPhoneNumber(userId: String): String? {
        val listOfPhone = phoneNumberService.getPhoneNumberInfo(userId).block();
        return if (listOfPhone is List<String>) {
            listOfPhone[0]
        } else null
    }

    private fun creatingListOfImages(currentCar: JsonObject, size: String) : Set<String> {
        val images = mutableSetOf<String>()
        val map = mapOf(
            "OG" to "original",
            "1900" to "1900x1180",
            "800" to "800x800",
            "600" to "600x370",
            "380" to "380x240",
            "100" to "100x100",
            "80" to "80x80"
        )
        currentCar.get("images").asJsonArray.forEach { images.add(it.asJsonObject.get(map[size]).asString) }
        return images
        }
}