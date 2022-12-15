package com.imaginato.homeworkmvvm.ui.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.imaginato.homeworkmvvm.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var uID: String
    private lateinit var uName: String
    private lateinit var uIsDel: String
    private lateinit var uXAcc: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDemo.setOnClickListener {
            viewModel.getDemoData()
        }

        uID = intent.getStringExtra("uid").toString()
        uName = intent.getStringExtra("uname").toString()
        uIsDel = intent.getStringExtra("uisdel").toString()
        uXAcc = intent.getStringExtra("x_acc").toString()

        binding.tvLoginData.text =
            "User ID : " + uID + "\n\n" + "Username : " + uName + "\n\n" + "User is Deleted : " +
                    uIsDel + "\n\n" + "User X-ACC : " + uXAcc   //Setting Login Details of logged in user in text view

        initObserve()
    }

    private fun initObserve() {
        viewModel.resultLiveData.observe(this, Observer {
            binding.tvResult.text = it
        })
        viewModel.progress.observe(this, Observer {
            binding.pbLoading.isVisible = it
        })
    }
}