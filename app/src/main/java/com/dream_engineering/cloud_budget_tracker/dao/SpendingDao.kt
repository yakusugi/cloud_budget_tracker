package com.dream_engineering.cloud_budget_tracker.dao

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dream_engineering.cloud_budget_tracker.dto.SpendingDto
import com.dream_engineering.cloud_budget_tracker.dto.UserDto
import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import com.dream_engineering.cloud_budget_tracker.view.AddSpendingFragment
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.Properties

class SpendingDao(context: Context)  {
    private val context: Context = context.applicationContext
    fun insertIntoSpending(spendingDto: SpendingDto): Int {
        try {
            val properties = Properties()
            val inputStream = context.assets.open("server_config.properties")
            properties.load(inputStream)
            val serverUrl = properties.getProperty("server_url")
            val phpInsertFile = properties.getProperty("spending_add_php_file")
            val insertUrl = "$serverUrl$phpInsertFile"
            Log.d("insert_url", insertUrl)

            val stringRequest = object : StringRequest(
                Method.POST, insertUrl,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")

                        if (success > 0.toString()) {
                            Toast.makeText(
                                context,
                                "User data has been inserted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } catch (e: JSONException) {
                        Log.e("JSONException", e.toString())
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (error is ServerError) {
                        val networkResponse = error.networkResponse
                        if (networkResponse != null) {
                            Log.e(
                                "Volley",
                                "Error. HTTP Status Code: ${networkResponse.statusCode}"
                            )
                        }
                    }
                    Toast.makeText(context, "Unable to insert data $error", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("VolleyError", error.toString())
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["email"] = SharedPreferencesManager.getUserEmail(context).toString()
                    params["date"] = spendingDto.date.toString()
                    params["store_name"] = spendingDto.storeName
                    params["product_name"] = spendingDto.productName
                    params["product_type"] = spendingDto.productType
                    params["vat_rate"] = spendingDto.vatRate.toString()
                    params["price"] = spendingDto.price.toString()
                    params["note"] = spendingDto.note

                    return params
                }
            }

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return 0
    }

}