package com.dream_engineering.cloud_budget_tracker.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.databinding.ActivityMainBinding
import com.dream_engineering.cloud_budget_tracker.dto.SpendingDto
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
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var searchNameText: EditText
    private lateinit var tvDatePickerFrom: TextView
    private lateinit var tvDatePickerTo: TextView
    private lateinit var searchButton: Button
    val date: LocalDate = LocalDate.now()

    private lateinit var binding : ActivityMainBinding
    private lateinit var spendingActivityList : java.util.ArrayList<SpendingDto>

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
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        // Inside your onCreateView method in AddSpendingFragment
        tvDatePickerFrom = rootView.findViewById(R.id.edit_text_spending_date_from)
        tvDatePickerTo = rootView.findViewById(R.id.edit_text_spending_date_to)

        val myCalendar = Calendar.getInstance()

        // Set an OnClickListener on the TextView(From)
        tvDatePickerFrom.setOnClickListener {
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
                    updateLabelFrom(myCalendar)
                },
                date.year, // Use the year from 'date'
                date.monthValue - 1, // Subtract 1 because months are 0-based in DatePickerDialog
                date.dayOfMonth // Use the day of the month from 'date'
            )
            // Show the date picker dialog
            datePicker.show()
        }

        // Set an OnClickListener on the TextView(To)
        tvDatePickerTo.setOnClickListener {
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
                    updateLabelTo(myCalendar)
                },
                date.year, // Use the year from 'date'
                date.monthValue - 1, // Subtract 1 because months are 0-based in DatePickerDialog
                date.dayOfMonth // Use the day of the month from 'date'
            )
            // Show the date picker dialog
            datePicker.show()
        }


        searchNameText = rootView.findViewById(R.id.edit_text_spending_store_name)


        // Inflate the layout for this fragment
        return rootView
    }

    private fun updateLabelFrom(myCalender: Calendar) {
        val myFormat = "MM-dd-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvDatePickerFrom.setText(sdf.format(myCalender.time))
    }

    private fun updateLabelTo(myCalender: Calendar) {
        val myFormat = "MM-dd-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvDatePickerTo.setText(sdf.format(myCalender.time))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}