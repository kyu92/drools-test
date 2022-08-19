package com.kyu.drools.controller

import com.kyu.drools.entity.VerifyResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/")
    fun test(verifyResult: VerifyResult) {
        println("Hello Drools, result: $verifyResult")
    }
}