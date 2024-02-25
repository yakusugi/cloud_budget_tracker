package com.dream_engineering.cloud_budget_tracker.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.dao.SpendingInsertDao
import com.dream_engineering.cloud_budget_tracker.dto.SpendingDto
import com.dream_engineering.cloud_budget_tracker.dto.UserDto
import com.dream_engineering.cloud_budget_tracker.utils.SharedPreferencesManager
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddSpendingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddSpendingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var tvDatePicker: TextView
    private lateinit var storeNameText: EditText
    private lateinit var productNameText: EditText
    private lateinit var productTypeText: EditText
    private lateinit var vatRateText: EditText
    private lateinit var priceText: EditText
    private lateinit var noteText: EditText
    private lateinit var currencyCodeText: EditText
    private lateinit var addSpendingButton: Button
    var date: LocalDate = LocalDate.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_spending, container, false)

        val myCalender = Calendar.getInstance()

        // Inside your onCreateView method in AddSpendingFragment
        tvDatePicker = rootView.findViewById(R.id.add_spending_date)

        val myCalendar = Calendar.getInstance()

        tvDatePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, month, dayOfMonth ->
                    // Update dateFrom with the selected date
                    date = LocalDate.of(year, month + 1, dayOfMonth)
                    val formattedDate = date.toString()
                    tvDatePicker.text = formattedDate
                }, date.year, date.monthValue - 1, date.dayOfMonth
            )
            datePickerDialog.show()
        }


        storeNameText = rootView.findViewById(R.id.add_spending_store_name)
        productNameText = rootView.findViewById(R.id.add_spending_product_name)
        productTypeText = rootView.findViewById(R.id.add_spending_product_type)
        vatRateText = rootView.findViewById(R.id.add_spending_vate_rate)
        priceText = rootView.findViewById(R.id.add_spending_price)
        noteText = rootView.findViewById(R.id.add_spending_note)
        currencyCodeText = rootView.findViewById(R.id.add_spending_currency_code)
        addSpendingButton = rootView.findViewById(R.id.spending_add_btn)

        addSpendingButton.setOnClickListener {
            Log.d("MyTag", "The content of myVariable is: ${date}")

            val selectedDateInMillis =
                myCalendar.timeInMillis // Get the selected date in milliseconds

//            val dateForInsert = convertLocalDateToDate(date)
            val storeName: String = storeNameText.getText().toString()
            val productName: String = productNameText.getText().toString()
            val productType: String = productTypeText.getText().toString()
            val vatRate: Double = vatRateText.getText().toString().toDouble()
            val price: Double = priceText.getText().toString().toDouble()
            val note: String = noteText.getText().toString()
            val currencyCode: String = currencyCodeText.getText().toString()

            try {
                val spendingDto =
                    SpendingDto(date, storeName, productName, productType, vatRate, price, note, currencyCode)

                val spendingInsertDao = SpendingInsertDao(requireContext())
                spendingInsertDao.insertIntoSpending(spendingDto)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return rootView
//        return inflater.inflate(R.layout.fragment_add_spending, container, false)
    }

    private fun updateLabel(myCalender: Calendar) {
        val myFormat = "MM-dd-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvDatePicker.setText(sdf.format(myCalender.time))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddSpendingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddSpendingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}