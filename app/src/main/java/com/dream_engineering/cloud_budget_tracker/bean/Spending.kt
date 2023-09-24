package com.dream_engineering.cloud_budget_tracker.bean

import java.time.LocalDate

data class Spending(var spendingDate: LocalDate, var storeName: String, var productName: String, var productType: String,
                    var vatRate: Double, var price: Double, var note: String, var currencyCode: String, var quantity: Int)
