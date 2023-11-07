package com.dream_engineering.cloud_budget_tracker.view

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
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.dao.IncomeDao
import com.dream_engineering.cloud_budget_tracker.dao.SpendingDao
import com.dream_engineering.cloud_budget_tracker.dto.IncomeDto
import com.dream_engineering.cloud_budget_tracker.dto.SpendingDto
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddIncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddIncomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var tvDatePicker: TextView
    private lateinit var incomeNameText: EditText
    private lateinit var incomeCategoryText: EditText
    private lateinit var incomeText: EditText
    private lateinit var noteText: EditText
    private lateinit var addIncomeButton: Button
    val date: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_income, container, false)

        tvDatePicker = rootView.findViewById(R.id.add_income_date)

        val myCalendar = Calendar.getInstance()

        // Set an OnClickListener on the TextView
        tvDatePicker.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    // Use the 'date' variable here if needed
                    val selectedDate = LocalDate.of(
                        year,
                        month + 1,
                        dayOfMonth
                    ) // +1 because months are 0-based in DatePickerDialog
                    // Now you can use 'selectedDate' as the selected date
                    updateLabel(myCalendar)
                },
                date.year, // Use the year from 'date'
                date.monthValue - 1, // Subtract 1 because months are 0-based in DatePickerDialog
                date.dayOfMonth // Use the day of the month from 'date'

            )
            // Show the date picker dialog
            datePicker.show()
        }

        incomeNameText = rootView.findViewById(R.id.add_income_name)
        incomeCategoryText = rootView.findViewById(R.id.add_income_category_name)
        incomeText = rootView.findViewById(R.id.add_income)
        noteText = rootView.findViewById(R.id.add_spending_note)
        addIncomeButton = rootView.findViewById(R.id.income_add_btn)

        addIncomeButton.setOnClickListener {
            Log.d("MyTag", "The content of myVariable is: ${date}")

            val selectedDateInMillis =
                myCalendar.timeInMillis // Get the selected date in milliseconds

//            val dateForInsert = convertLocalDateToDate(date)
            val incomeName: String = incomeNameText.getText().toString()
            val incomeCategoryName: String = incomeCategoryText.getText().toString()
            val income: Double = incomeText.getText().toString().toDouble()
            val note: String = noteText.getText().toString()

            try {
                val incomeDto =
                    IncomeDto(date, incomeName, incomeCategoryName, income, note)

                val incomeDao = IncomeDao(requireContext())
                incomeDao.insertIntoIncome(incomeDto)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }



        return rootView
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
         * @return A new instance of fragment AddIncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddIncomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}