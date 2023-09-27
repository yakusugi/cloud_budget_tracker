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
import com.dream_engineering.cloud_budget_tracker.dto.SpendingDto

class SpendingSearchAdapter(private val context: Activity, private val arrayList: ArrayList<SpendingDto>): ArrayAdapter<SpendingDto>(context,
    R.layout.list_item, arrayList) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val imageView: ImageView = view.findViewById(R.id.expense_pic)
        val dateView: TextView = view.findViewById(R.id.spending_date_txt)
        val storeName: TextView = view.findViewById(R.id.spending_store_name_txt)
        val productName: TextView = view.findViewById(R.id.spending_product_name_txt)
        val productType: TextView = view.findViewById(R.id.spending_product_type_txt)
        val vat_rate: TextView = view.findViewById(R.id.spending_vat_rate_txt)
        val price: TextView = view.findViewById(R.id.spending_price_txt)
        val note: TextView = view.findViewById(R.id.spending_note_txt)
        val currencyCode: TextView = view.findViewById(R.id.spending_currency_code_txt)
        val quantity: TextView = view.findViewById(R.id.spending_quantity_txt)

//        imageView.setImageResource(arrayList[position].imageId)
        dateView.text = arrayList[position].date.toString()
        storeName.text = arrayList[position].storeName
        productName.text = arrayList[position].productName
        productType.text = arrayList[position].productType
        vat_rate.text = arrayList[position].vatRate.toString()
        price.text = arrayList[position].price.toString()
        note.text = arrayList[position].note
        currencyCode.text = arrayList[position].currencyCode
        quantity.text = arrayList[position].quantity.toString()


        return view
    }

}