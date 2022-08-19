package com.kyu.drools.entity

data class Order(private var orderCode: String) {

    var loanAmount = 0L
    var score: Int? = null
}
