package com.dream_engineering.cloud_budget_tracker.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.dto.IncomeDto

class IncomeSearchAdapter(private val context: Activity, private val arrayList: ArrayList<IncomeDto>): ArrayAdapter<IncomeDto>(context,
    R.layout.list_item_income, arrayList) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_income, null)

        val imageView: ImageView = view.findViewById(R.id.income_pic)
        val dateView: TextView = view.findViewById(R.id.income_date_txt)
        val incomeName: TextView = view.findViewById(R.id.income_name_txt)
        val incomeCategoryName: TextView = view.findViewById(R.id.income_category_name_txt)
        val income: TextView = view.findViewById(R.id.income_txt)
        val note: TextView = view.findViewById(R.id.income_note_txt)
        val currencyCode: TextView = view.findViewById(R.id.income_currency_code_txt)

//        imageView.setImageResource(arrayList[position].imageId)
        dateView.text = arrayList[position].date.toString()
        incomeName.text = arrayList[position].incomeName
        incomeCategoryName.text = arrayList[position].incomeCategoryName
        income.text = arrayList[position].income.toString()
        note.text = arrayList[position].note
        currencyCode.text = arrayList[position].currencyCode

        return view
    }

}