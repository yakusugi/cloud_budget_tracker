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
import android.widget.ListView
import android.widget.TextView
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.adapter.SpendingSearchAdapter
import com.dream_engineering.cloud_budget_tracker.dao.SpendingDao
import com.dream_engineering.cloud_budget_tracker.databinding.ActivityMainBinding
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
    val dateFrom: LocalDate = LocalDate.now()
    val dateTo: LocalDate = LocalDate.now()

    private lateinit var binding : ActivityMainBinding
    private lateinit var spendingActivityList : java.util.ArrayList<SpendingDto>
    private lateinit var listView: ListView
    private lateinit var adapter: SpendingSearchAdapter

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
        spendingActivityList = ArrayList()
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        listView = rootView.findViewById(R.id.search_list_view)
        adapter = SpendingSearchAdapter(requireActivity(), spendingActivityList)
        listView.adapter = adapter

        var selectedDate = LocalDate.now()

        val myCalendar = Calendar.getInstance()

        // Inside your onCreateView method in AddSpendingFragment
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        tvDatePickerFrom = rootView.findViewById(R.id.edit_text_spending_date_from)
        tvDatePickerTo = rootView.findViewById(R.id.edit_text_spending_date_to)
//        tvDatePickerFrom.text = dateFormat.format(myCalendar.time)
//        tvDatePickerTo.text = dateFormat.format(myCalendar.time)



        // Set an OnClickListener on the TextView(From)
        tvDatePickerFrom.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    // Use the 'date' variable here if needed
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    // Now you can use 'selectedDate' as the selected date
                    updateLabelFrom(myCalendar)
                },
                dateFrom.year, // Use the year from 'date'
                dateFrom.monthValue - 1, // Subtract 1 because months are 0-based in DatePickerDialog
                dateFrom.dayOfMonth // Use the day of the month from 'date'
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
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)

                    updateLabelTo(myCalendar)
                },
                dateTo.year, // Use the year from 'date'
                dateTo.monthValue - 1, // Subtract 1 because months are 0-based in DatePickerDialog
                dateTo.dayOfMonth // Use the day of the month from 'date'
            )
            // Show the date picker dialog
            datePicker.show()
        }


        searchNameText = rootView.findViewById(R.id.edit_text_spending_store_name)

        searchButton = rootView.findViewById(R.id.search_btn)

        searchButton.setOnClickListener {
            val storeName: String = searchNameText.getText().toString()

            try {
                val spendingDto =
                    SpendingDto(storeName, dateFrom, dateTo)

                val spendingDao = SpendingDao(requireContext())
                spendingDao.selectSpendingData(spendingDto,
                    onSuccess = { spendingList ->
                        spendingActivityList.clear()
                        spendingActivityList.addAll(spendingList)
                        // Notify the adapter that the data has changed
                        adapter.notifyDataSetChanged()
                },
                    onError = { errorMessage ->
                        // Handle error here, you can access the error message
                        // This block of code will be executed when there is an error in the API request
                        // You can display an error message to the user or perform error handling actions
                    })
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        // Inflate the layout for this fragment
        return rootView
    }

    private fun updateLabelFrom(myCalender: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tvDatePickerFrom.setText(sdf.format(myCalender.time))
    }

    private fun updateLabelTo(myCalender: Calendar) {
        val myFormat = "yyyy-MM-dd"
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