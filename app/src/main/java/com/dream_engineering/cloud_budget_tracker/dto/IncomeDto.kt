package com.dream_engineering.cloud_budget_tracker.dto

import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import java.time.LocalDate
import java.util.Date

class IncomeDto {
    lateinit var date: LocalDate
    lateinit var dateFrom: LocalDate // Add dateFrom property
    lateinit var dateTo: LocalDate // Add dateTo property
    var incomeName: String = ""
    var incomeCategoryName: String = ""
    var income: Double = 0.0
    var note: String = ""
    var currencyCode: String = ""





    constructor(
        date: LocalDate,
        dateFrom: LocalDate,
        dateTo: LocalDate,
        incomeName: String,
        incomeCategoryName: String,
        income: Double,
        note: String,
        currencyCode: String
    ) {
        this.date = date
        this.dateFrom = dateFrom
        this.dateTo = dateTo
        this.incomeName = incomeName
        this.incomeCategoryName = incomeCategoryName
        this.income = income
        this.note = note
        this.currencyCode = currencyCode
    }

    constructor(
        date: LocalDate,
        incomeName: String,
        incomeCategoryName: String,
        income: Double,
        note: String
    ) {
        this.date = date
        this.incomeName = incomeName
        this.incomeCategoryName = incomeCategoryName
        this.income = income
        this.note = note
    }

    constructor(incomeName: String, dateFrom: LocalDate, dateTo: LocalDate) {
        this.incomeName = incomeName
        this.dateFrom = dateFrom
        this.dateTo = dateTo
    }

    constructor(
        dateTo: LocalDate,
        incomeName: String,
        incomeCategoryName: String,
        income: Double,
        note: String,
        currencyCode: String
    ) {
        this.dateTo = dateTo
        this.incomeName = incomeName
        this.incomeCategoryName = incomeCategoryName
        this.income = income
        this.note = note
        this.currencyCode = currencyCode
    }
}