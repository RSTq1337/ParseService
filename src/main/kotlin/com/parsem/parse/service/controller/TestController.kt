package com.parsem.parse.service.controller

import com.parsem.parse.service.serivce.api.onliner.BasePageInfoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController(
    var basePageInfoService: BasePageInfoService
) {
    @GetMapping("test")
    @ResponseStatus(code = HttpStatus.OK)
    fun test() {
        basePageInfoService.getBasePageInfo()
    }
}
