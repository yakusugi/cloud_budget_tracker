package com.dream_engineering.cloud_budget_tracker.dao

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dream_engineering.cloud_budget_tracker.dto.SpendingDto
import com.dream_engineering.cloud_budget_tracker.dto.UserDto
import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import com.dream_engineering.cloud_budget_tracker.view.AddSpendingFragment
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDate
import java.util.Properties

class SpendingProductNameDao(context: Context)  {
    private val context: Context = context.applicationContext

    //for product name search
    fun selectSpendingProductNameData(
        spendingDto: SpendingDto,
        onSuccess: (List<SpendingDto>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val properties = Properties()
            val inputStream = context.assets.open("server_config.properties")
            properties.load(inputStream)
            val serverUrl = properties.getProperty("server_url")
            val phpSelectFile = properties.getProperty("spending_product_name_search_php_file")
            val selectUrl = "$serverUrl$phpSelectFile"
            Log.d("select_url", selectUrl)

            // Create a map of parameters to send in the POST request
            val params = HashMap<String, String>()
            params["email"] = SharedPreferencesManager.getUserEmail(context).toString()
            params["product_name"] = spendingDto.storeName
//            params["product_name"] = spendingDto.productName
            params["date_from"] = spendingDto.dateFrom.toString()
            params["date_to"] = spendingDto.dateTo.toString()

            val stringRequest = object : StringRequest(
                Request.Method.POST, selectUrl,
                Response.Listener { response ->
                    Log.d("JSONResponse", response) // Log the JSON response
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.optString("success", "")

                        if (success == "1") {
                            val jsonArray = jsonObject.optJSONArray("result")
                            if (jsonArray != null) {
                                val spendingList = mutableListOf<SpendingDto>()
                                for (i in 0 until jsonArray.length()) {
                                    val jsonObject = jsonArray.getJSONObject(i)
                                    // Parse data from the JSON object
                                    val date = jsonObject.getString("spending_date")
                                    val storeName = jsonObject.getString("store_name")
                                    val productName = jsonObject.getString("product_name")
                                    val productType = jsonObject.getString("product_type")
                                    val vatRateString = jsonObject.getString("vat_rate")
                                    val priceString = jsonObject.getString("price")
                                    val note = jsonObject.getString("note")
                                    val currencyCode = jsonObject.getString("currency_code")
                                    val quantityString = jsonObject.getString("quantity")

                                    val vatRate = vatRateString.toDoubleOrNull() ?: 0.0 // Convert to Double or default to 0.0 if conversion fails
                                    val price = priceString.toDoubleOrNull() ?: 0.0 // Convert to Double or default to 0.0 if conversion fails
                                    val quantity = quantityString.toIntOrNull() ?: 0 // Convert to Int or default to 0 if conversion fails

                                    val spendingDto = SpendingDto(
                                        LocalDate.parse(date),
                                        storeName,
                                        productName,
                                        productType,
                                        vatRate,
                                        price,
                                        note,
                                        currencyCode,
                                        quantity
                                    )
                                    spendingList.add(spendingDto)
                                }

                                // Invoke the onSuccess callback with the list of SpendingDto objects
                                onSuccess(spendingList)
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
                // Add parameters to the request body
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