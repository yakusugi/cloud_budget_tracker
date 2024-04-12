package com.dream_engineering.cloud_budget_tracker.dto

import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import java.time.LocalDate
import java.util.Date

class SpendingProductNameSumDto {
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

    constructor(productName: String, dateFrom: LocalDate, dateTo: LocalDate) {
        this.productName = productName
        this.dateFrom = dateFrom
        this.dateTo = dateTo
    }

}