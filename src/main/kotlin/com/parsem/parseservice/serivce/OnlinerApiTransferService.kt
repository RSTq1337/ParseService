package com.parsem.parseservice.serivce

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.parsem.parseservice.dto.CarDataFromOnliner
import org.springframework.stereotype.Service

@Service
class OnlinerApiTransferService (
    private var phoneNumberService: PhoneNumberService
){


    fun transform(json: String?): Set<CarDataFromOnliner> {
        var normalJson = JsonParser.parseString(json).asJsonObject.get("adverts").asJsonArray
        var setOfCars = mutableSetOf<CarDataFromOnliner>()
        for (currentCar in normalJson) {
            var currentCarJsonObject = currentCar.asJsonObject
            var result = CarDataFromOnliner(
                title = currentCarJsonObject.get("title").asString,
                authorId = currentCarJsonObject.get("author").asJsonObject.get("id").asString,
                brand = currentCarJsonObject.get("manufacturer").asJsonObject.get("name").asString,
                model = currentCarJsonObject.get("model").asJsonObject.get("name").asString,
                generation = currentCarJsonObject.get("generation").asJsonObject.get("name").asString,
                transmission = currentCarJsonObject.get("specs").asJsonObject.get("transmission").asString,
                odometer = currentCarJsonObject.get("specs").asJsonObject.get("odometer").asJsonObject.get("unit").asString,
                year = currentCarJsonObject.get("specs").asJsonObject.get("year").asString,
                color = currentCarJsonObject.get("specs").asJsonObject.get("color").asString,
                bodyType = currentCarJsonObject.get("specs").asJsonObject.get("body_type").asString,
                state = currentCarJsonObject.get("specs").asJsonObject.get("state").asString,
                driveTrain = currentCarJsonObject.get("specs").asJsonObject.get("drivetrain").asString,
                modification = when(
                    currentCarJsonObject.get("specs").asJsonObject.get("modification").isJsonNull
                ){
                    true -> null
                    false -> currentCarJsonObject.get("specs").asJsonObject.get("modification").asString
                },
                engineType = when(
                    currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("type").isJsonNull
                ) {
                  true -> null
                  false -> currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("type").asString
                  },
                engineCapacity = when(
                    currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("capacity").isJsonNull
                ) {
                    true -> null
                    false -> currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("capacity").asString
                  },
                engineTorque = when(
                    currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("torque").isJsonNull
                ){
                    true -> null
                    false -> currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("torque").asString
                  },
                enginePower = when(
                    currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("power").isJsonNull
                ) {
                    true -> null
                    false -> currentCarJsonObject.get("specs").asJsonObject.get("engine").asJsonObject.get("power").asJsonObject.get("value").asString
                },
                sellerNumber = fillingPhoneNumber(currentCarJsonObject.get("author").asJsonObject.get("id").asString).orEmpty(),
                priceUSD = currentCarJsonObject.get("price").asJsonObject.get("converted").asJsonObject.get("USD").asJsonObject.get("amount").asString,
                priceBYN =  currentCarJsonObject.get("price").asJsonObject.get("converted").asJsonObject.get("BYN").asJsonObject.get("amount").asString,
                imagesOG = creatingListOfImages(currentCarJsonObject, "OG"),
                images1900 = creatingListOfImages(currentCarJsonObject, "1900"),
                images800 = creatingListOfImages(currentCarJsonObject, "800"),
                images600 = creatingListOfImages(currentCarJsonObject, "600") ,
                images380 = creatingListOfImages(currentCarJsonObject, "380") ,
                images100 = creatingListOfImages(currentCarJsonObject, "100") ,
                images80 = creatingListOfImages(currentCarJsonObject, "80") ,
                created = currentCarJsonObject.get("created_at").asString,
                url = currentCarJsonObject.get("url").asString

                )
            setOfCars.add(result)
            }
            return setOfCars
        }
    fun fillingPhoneNumber(userId: String): String? {
        return phoneNumberService.getPhoneNumber(userId)
    }

    private fun creatingListOfImages(currentCar: JsonObject, size: String) : Set<String> {
        var images = mutableSetOf<String>()
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