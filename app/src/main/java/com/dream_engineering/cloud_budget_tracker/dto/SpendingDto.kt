package com.dream_engineering.cloud_budget_tracker.dto

import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import java.time.LocalDate
import java.util.Date

class SpendingDto {
    lateinit var date: LocalDate
    lateinit var dateFrom: LocalDate // Add dateFrom property
    lateinit var dateTo: LocalDate // Add dateTo property
    var spendingCalcSum: Double = 0.0 // Calculated sum
    var storeName: String = ""
    var productName: String = ""
    var productType: String = ""
    var vatRate: Double = 0.0
    var price: Double = 0.0
    var note: String = ""
    var currencyCode: String = ""
    var quantity: Int = 0

    constructor(spendingCalcSum: Double) {
        this.spendingCalcSum = spendingCalcSum
    }

    constructor(storeName: String, dateFrom: LocalDate, dateTo: LocalDate) {
        this.storeName = storeName
        this.dateFrom = dateFrom
        this.dateTo = dateTo
    }

    constructor(
        date: LocalDate,
        storeName: String,
        productName: String,
        productType: String,
        vatRate: Double,
        price: Double,
        note: String,
        currencyCode: String
    ) {
        this.date = date
        this.storeName = storeName
        this.productName = productName
        this.productType = productType
        this.vatRate = vatRate
        this.price = price
        this.note = note
        this.currencyCode = currencyCode
    }

    constructor(
        date: LocalDate,
        storeName: String,
        productName: String,
        productType: String,
        vatRate: Double,
        price: Double,
        note: String,
        currencyCode: String,
        quantity: Int
    ) {
        this.date = date
        this.storeName = storeName
        this.productName = productName
        this.productType = productType
        this.vatRate = vatRate
        this.price = price
        this.note = note
        this.currencyCode = currencyCode
        this.quantity = quantity
    }
}