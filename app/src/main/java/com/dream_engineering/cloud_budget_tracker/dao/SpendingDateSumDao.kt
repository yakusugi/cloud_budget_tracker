package com.dream_engineering.cloud_budget_tracker.dao

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dream_engineering.cloud_budget_tracker.dto.SpendingDto
import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.util.Properties

class SpendingDateSumDao(context: Context) {
    private val context: Context = context.applicationContext

    fun selectSpendingStoreCalc(
        spendingDto: SpendingDto,
        onSuccess: (Double) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val properties = Properties()
            val inputStream = context.assets.open("server_config.properties")
            properties.load(inputStream)
            val serverUrl = properties.getProperty("server_url")
            val phpSelectFile = properties.getProperty("spending_date_search_sum_php_file")
            val selectUrl = "$serverUrl$phpSelectFile"
            Log.d("select_url", selectUrl)

            val params = HashMap<String, String>()
            params["email"] = SharedPreferencesManager.getUserEmail(context).toString()
            params["currency_code"] = spendingDto.currencyCode
            params["date_from"] = spendingDto.dateFrom.toString()
            params["date_to"] = spendingDto.dateTo.toString()

            val stringRequest = object : StringRequest(
                Request.Method.POST, selectUrl,
                Response.Listener { response ->
                    Log.d("JSONResponse", response)
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.optString("success", "")
                        if (success == "1") {
                            val jsonArray = jsonObject.optJSONArray("result")
                            if (jsonArray != null) {
                                var totalSpending = 0.0
                                for (i in 0 until jsonArray.length()) {
                                    val jsonObject = jsonArray.getJSONObject(i)
                                    val spendingCalcSum = jsonObject.getString("total_sum")
                                    val spendingCalcSumDouble = spendingCalcSum.toDoubleOrNull() ?: 0.0
                                    totalSpending += spendingCalcSumDouble
                                }
                                // Invoke the onSuccess callback with the total spending sum
                                onSuccess(totalSpending)
                            } else {
                                onError("No spending data found")
                            }
                        } else {
                            val errorMessage = jsonObject.optString("error_message", "Error parsing JSON")
                            onError(errorMessage)
                        }
                    } catch (e: JSONException) {
                        Log.e("JSONException", e.toString())
                        e.printStackTrace()
                        onError("Error parsing JSON")
                    }
                },
                Response.ErrorListener { error ->
                    onError("Unable to fetch data: ${error.message}")
                    Log.e("VolleyError", error.toString())
                }) {
                override fun getParams(): Map<String, String> {
                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        } catch (e: IOException) {
            e.printStackTrace()
            onError("Error loading server configuration")
        }
    }

}