package com.imaginato.homeworkmvvm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.data.local.login.Login
import com.imaginato.homeworkmvvm.data.local.login.LoginDatabase
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import com.imaginato.homeworkmvvm.databinding.ActivityLoginBinding
import com.imaginato.homeworkmvvm.ui.demo.MainActivity
import com.imaginato.homeworkmvvm.ui.login.Entity.LoginEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginActivityViewModel>()
    private lateinit var binding: ActivityLoginBinding

    lateinit var db: LoginDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(this, LoginDatabase::class.java, "LoginDB")
            .fallbackToDestructiveMigration()
            .build()

        binding.btnLogin.setOnClickListener {

            if (!binding.edtUsername.text.isNullOrBlank()) {
                if (!binding.edtPassword.text.isNullOrBlank()) {

                    var strUName = binding.edtUsername.text.toString()
                    var strPass = binding.edtPassword.text.toString()

                    viewModel.createLoginData(strUName, strPass)

                } else {
                    binding.edtPassword.error = "Password required"
                    binding.edtPassword.requestFocus()
                }
            } else {
                binding.edtUsername.error = "Username required"
                binding.edtUsername.requestFocus()
            }

        }
        initObserve()
    }

    private fun initObserve() {
        viewModel.resultLiveData.observe(this, Observer {

            var loginResponse: LoginResponse = it

            var uid = loginResponse.data?.userId.toString()
            var uname = loginResponse.data?.userName.toString()
            var uisdel = loginResponse.data?.isDeleted.toString()
            var uxacc = loginResponse.x_acc.toString()

            var login = Login(uxacc, uid, uname, uisdel)

            if (loginResponse.errorCode == "00") {
                //Success (If user found in Database and credentials are correct)
                Toast.makeText(this, getString(R.string.str_login_success), Toast.LENGTH_SHORT)
                    .show()

                GlobalScope.launch() {
                    db.loginDao.insertLoginData(login)  //Write data to the Database
                }

                binding.edtUsername.text.clear()
                binding.edtPassword.text.clear()
                binding.btnLogin.isEnabled =
                    true   //Required, so user can't press another time while API is in call

                val i = Intent(this, MainActivity::class.java)
                //Passing Data to Main Activity for displaying
                i.putExtra("x_acc", uxacc)
                i.putExtra("uid", uid)
                i.putExtra("uname", uname)
                i.putExtra("uisdel", uisdel)
                startActivity(i)
                finish()

            } else {
                // Error (If user not found in Database and credentials are incorrect)
                Toast.makeText(this, loginResponse.errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.progress.observe(this, Observer {
            binding.pbLoading.isVisible = it
            binding.btnLogin.isEnabled = false
        })
    }

}