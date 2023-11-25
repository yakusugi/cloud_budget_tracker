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
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.adapter.IncomeSearchAdapter
import com.dream_engineering.cloud_budget_tracker.dao.IncomeDao
import com.dream_engineering.cloud_budget_tracker.databinding.ActivityMainBinding
import com.dream_engineering.cloud_budget_tracker.dto.IncomeDto
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IncomeSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomeSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var searchNameText: EditText
    private lateinit var tvDatePickerFrom: TextView
    private lateinit var tvDatePickerTo: TextView
    private lateinit var searchButton: Button
    var dateFrom: LocalDate = LocalDate.now()
    var dateTo: LocalDate = LocalDate.now()

    private lateinit var binding : ActivityMainBinding
    private lateinit var incomeActivityList : java.util.ArrayList<IncomeDto>
    private lateinit var listView: ListView
    private lateinit var adapter: IncomeSearchAdapter

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
        incomeActivityList = ArrayList()
        val rootView = inflater.inflate(R.layout.fragment_income_search, container, false)
        listView = rootView.findViewById(R.id.income_search_list_view)
        adapter = IncomeSearchAdapter(requireActivity(), incomeActivityList)
        listView.adapter = adapter

        var selectedDate = LocalDate.now()

        // Inside your onCreateView method in AddSpendingFragment
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        tvDatePickerFrom = rootView.findViewById(R.id.edit_text_income_date_from)
        tvDatePickerTo = rootView.findViewById(R.id.edit_text_income_date_to)
        val radioGroup: RadioGroup = rootView.findViewById(R.id.income_radio_group)

        val calendar = android.icu.util.Calendar.getInstance()
        val year = calendar[android.icu.util.Calendar.YEAR]
        val month = calendar[android.icu.util.Calendar.MONTH]
        val day = calendar[android.icu.util.Calendar.DAY_OF_MONTH]
        dateFrom = LocalDate.of(year, month + 1, day)
        dateTo = LocalDate.of(year, month + 1, day)

        tvDatePickerFrom.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, month, dayOfMonth ->
                    // Update dateFrom with the selected date
                    dateFrom = LocalDate.of(year, month + 1, dayOfMonth)
                    val formattedDate = dateFrom.toString()
                    tvDatePickerFrom.text = formattedDate
                }, dateFrom.year, dateFrom.monthValue - 1, dateFrom.dayOfMonth
            )
            datePickerDialog.show()
        }

        tvDatePickerTo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireActivity(),
                { _, year, month, dayOfMonth ->
                    // Update dateTo with the selected date
                    dateTo = LocalDate.of(year, month + 1, dayOfMonth)
                    val formattedDate = dateTo.toString()
                    tvDatePickerTo.text = formattedDate
                }, dateTo.year, dateTo.monthValue - 1, dateTo.dayOfMonth
            )
            datePickerDialog.show()
        }


        searchNameText = rootView.findViewById(R.id.edit_text_income_name)

        searchButton = rootView.findViewById(R.id.income_search_btn)

        searchButton.setOnClickListener {
            val searchKey: String = searchNameText.getText().toString()
            val dateFromLocal: LocalDate = LocalDate.parse(tvDatePickerFrom.text.toString())
            val dateToLocal: LocalDate = LocalDate.parse(tvDatePickerTo.text.toString())

            if (radioGroup.getCheckedRadioButtonId() == R.id.search_income_name_radio) {
                try {
                    val incomeDto =
                        IncomeDto(searchKey, dateFromLocal, dateToLocal)

                    val incomeDao = IncomeDao(requireContext())
                    incomeDao.selectIncomeNameData(incomeDto,
                        onSuccess = { incomeList ->
                            incomeActivityList.clear()
                            incomeActivityList.addAll(incomeList)
                            adapter.notifyDataSetChanged()
                        },
                        onError = { errorMessage ->
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                            Log.d("SearchFragment", errorMessage)
                        })
                } catch (e: IOException) {
                    e.printStackTrace()
                }
//            } else if (radioGroup.getCheckedRadioButtonId() == R.id.search_income_category_radio) {
//                try {
//                    val incomeDto =
//                        IncomeDto(searchKey, dateFromLocal, dateToLocal)
//
//                    val spendingDao = SpendingDao(requireContext())
//                    spendingDao.selectSpendingProductNameData(IncomeDto,
//                        onSuccess = { incomeList ->
//                            incomeActivityList.clear()
//                            incomeActivityList.addAll(incomeList)
//                            adapter.notifyDataSetChanged()
//                        },
//                        onError = { errorMessage ->
//                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
//                            Log.d("SearchFragment", errorMessage)
//                        })
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
            }

        }

        // Inflate the layout for this fragment
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IncomeSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IncomeSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}