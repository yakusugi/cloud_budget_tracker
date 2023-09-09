package com.dream_engineering.cloud_budget_tracker.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.dream_engineering.cloud_budget_tracker.R
import java.text.SimpleDateFormat
import java.util.Calendar
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
    private lateinit var storeName: EditText
    private lateinit var productName: EditText
    private lateinit var productType: EditText
    private lateinit var vatRate: EditText
    private lateinit var price: EditText
    private lateinit var note: EditText


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
        val rootView = inflater.inflate(R.layout.fragment_add_spending, container, false)

        val myCalender = Calendar.getInstance()

        // Inside your onCreateView method in AddSpendingFragment
        tvDatePicker = rootView.findViewById(R.id.add_spending_date)

        val myCalendar = Calendar.getInstance()

        // Set an OnClickListener on the TextView
        tvDatePicker.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLabel(myCalendar)
                },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )

            // Show the date picker dialog
            datePicker.show()
        }

        storeName = rootView.findViewById(R.id.add_spending_store_name)
        productName = rootView.findViewById(R.id.add_spending_product_name)
        productType = rootView.findViewById(R.id.add_spending_product_type)
        vatRate = rootView.findViewById(R.id.add_spending_vate_rate)
        price = rootView.findViewById(R.id.add_spending_price)
        note = rootView.findViewById(R.id.add_spending_note)



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