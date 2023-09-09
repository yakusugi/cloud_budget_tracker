package com.dream_engineering.cloud_budget_tracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.databinding.ActivityMainBinding
import com.getbase.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_botton_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private lateinit var binding: ActivityMainBinding
//    val binding = ActivityMainBinding.inflate(layoutInflater)


    private var clicked = false
    private lateinit var addBtn: FloatingActionButton
    private lateinit var spendingAddBtn: FloatingActionButton
    private lateinit var incomeAddBtn: FloatingActionButton
    private lateinit var bankAddBtn: FloatingActionButton

    fun getAddBtn(): FloatingActionButton {
        return addBtn
    }

    fun getSpendingBtn(): FloatingActionButton {
        return spendingAddBtn
    }

    fun getIncomeBtn(): FloatingActionButton {
        return incomeAddBtn
    }

    fun getBankBtn(): FloatingActionButton {
        return bankAddBtn
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot());

        addBtn = binding.floatingActionBtn
        spendingAddBtn = binding.spendingBtn
        incomeAddBtn = binding.incomeBtn
        bankAddBtn = binding.bankBtn

        setContentView(binding.root)
        replaceFragments(StatsFragment())

        addBtn.setOnClickListener() {
            onAddBtnClicked()
        }

        spendingAddBtn.setOnClickListener() {
            Toast.makeText(this, "spending btn clicked", Toast.LENGTH_SHORT).show()
            val fragment = AddSpendingFragment() // Replace with your fragment class

            // Begin a fragment transaction
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            // Replace the fragment_container with your fragment
            transaction.replace(R.id.frame_layout, fragment)

            // Add the transaction to the back stack (optional, for back navigation)
            transaction.addToBackStack(null)

            // Commit the transaction
            transaction.commit()
        }

        incomeAddBtn.setOnClickListener() {
            Toast.makeText(this, "income btn clicked", Toast.LENGTH_SHORT).show()
        }

        bankAddBtn.setOnClickListener() {
            Toast.makeText(this, "bank btn clicked", Toast.LENGTH_SHORT).show()
        }

        binding.bottomNavView.setOnItemReselectedListener {

            when(it.itemId) {
                R.id.stats -> replaceFragments(StatsFragment())
                R.id.search -> replaceFragments(SearchFragment())
                R.id.exchange -> replaceFragments(ExchangeFragment())

                else -> {
                }
            }
            true
        }


    }

    private fun replaceFragments(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun onAddBtnClicked() {
        setAnimation(clicked)
        setVisibility(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            spendingAddBtn.startAnimation(fromBottom)
            incomeAddBtn.startAnimation(fromBottom)
            bankAddBtn.startAnimation(fromBottom)
            addBtn.startAnimation(rotateOpen)
        } else {
            spendingAddBtn.startAnimation(toBottom)
            incomeAddBtn.startAnimation(toBottom)
            bankAddBtn.startAnimation(toBottom)
            addBtn.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            spendingAddBtn.visibility = View.VISIBLE
            incomeAddBtn.visibility = View.VISIBLE
            bankAddBtn.visibility = View.VISIBLE
        } else {
            spendingAddBtn.visibility = View.INVISIBLE
            incomeAddBtn.visibility = View.INVISIBLE
            bankAddBtn.visibility = View.INVISIBLE
        }
    }

}