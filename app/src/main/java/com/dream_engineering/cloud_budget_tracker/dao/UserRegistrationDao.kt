package com.dream_engineering.cloud_budget_tracker.dao

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dream_engineering.cloud_budget_tracker.dto.UserDto
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Method
import java.security.MessageDigest
import java.util.*
import kotlin.collections.HashMap

class UserRegistrationDao(context: Context)  {
    private val context: Context = context.applicationContext

    fun insertIntoUser(userDto: UserDto): Int {
        try {
            val properties = Properties()
            val inputStream = context.assets.open("server_config.properties")
            properties.load(inputStream)
            val serverUrl = properties.getProperty("server_url")
            val phpInsertFile = properties.getProperty("insert_php_file")
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
                    params["user_name"] = userDto.userName
                    params["password"] = hashPassword(userDto.password)
                    params["email"] = userDto.email

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

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun logIn(userDto: UserDto, callback: (result: Int) -> Unit) {
        var result = 0
        try {
            val properties = Properties()
            val inputStream = context.assets.open("server_config.properties")
            properties.load(inputStream)
            val serverUrl = properties.getProperty("server_url")
            val phpLoginFile = properties.getProperty("login_php_file")
            val loginUrl = "$serverUrl$phpLoginFile"
            Log.d("login_url", loginUrl)
            val stringRequest = object : StringRequest(
                Method.POST, loginUrl,
                Response.Listener { response ->
                    if (!response.isEmpty()) {
                        try {
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getInt("success")
                            if (success == 1) {
                                result = 1
                            }
                        } catch (e: JSONException) {
                            Log.e("JSONException", e.toString())
                        }
                    }
                    Log.e("response", response)
                    callback(result)
                },
                Response.ErrorListener { error ->
                    if (context != null) {
                        Toast.makeText(context, "Unable to send data $error", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("VolleyError", error.toString())
                    callback(result)
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["email"] = userDto.email
                    params["password"] = hashPassword(userDto.password) // Sending hashed password
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}