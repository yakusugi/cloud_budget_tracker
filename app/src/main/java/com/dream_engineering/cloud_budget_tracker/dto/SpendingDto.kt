package com.dream_engineering.cloud_budget_tracker.dto

import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import java.util.Date

class SpendingDto {

    lateinit var date: Date
    var storeName: String = ""
    var productName: String = ""
    var productType: String = ""
    var vatRate: Double = 0.0
    var price: Double = 0.0
    var note: String = ""

    constructor(
        date: Date,
        storeName: String,
        productName: String,
        productType: String,
        vatRate: Double,
        price: Double,
        note: String
    ) {
        this.date = date
        this.storeName = storeName
        this.productName = productName
        this.productType = productType
        this.vatRate = vatRate
        this.price = price
        this.note = note
    }
}