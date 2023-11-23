package com.dream_engineering.cloud_budget_tracker.view

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.dream_engineering.cloud_budget_tracker.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var incomeCard: CardView? = null
    private var spendingCard: CardView? = null
    private var bankCard: CardView? = null

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
        val view: View = inflater.inflate(R.layout.fragment_dash, container, false)
        incomeCard = view.findViewById<View>(R.id.income_card) as CardView
        spendingCard = view.findViewById<View>(R.id.spending_card) as CardView
        bankCard = view.findViewById<View>(R.id.bank_card) as CardView





        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        incomeCard = view.findViewById(R.id.income_card)
        spendingCard = view.findViewById(R.id.spending_card)
        bankCard = view.findViewById(R.id.bank_card)

        // Set OnClickListener for incomeCard
        incomeCard?.setOnClickListener {
            // Inside your fragment or activity where you want to perform the fragment transaction
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // Replace the current fragment with the new one
            val incomeSearchFragment = IncomeSearchFragment()
            fragmentTransaction.replace(R.id.frame_layout, incomeSearchFragment)

            // Add the transaction to the back stack so the user can navigate back
            fragmentTransaction.addToBackStack(null)

            // Commit the transaction
            fragmentTransaction.commit()


        }
    }






    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }





}