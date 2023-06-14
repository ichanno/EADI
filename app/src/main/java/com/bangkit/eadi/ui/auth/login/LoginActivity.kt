package com.bangkit.eadi.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.eadi.R

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bangkit.eadi.databinding.ActivityLoginBinding
import com.bangkit.eadi.ui.auth.repository.AuthRepository
import com.bangkit.eadi.ui.main.MainActivity
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.view.LoginViewModel
import com.example.storyapp.view.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authRepository = AuthRepository(this)
        val loginViewModelFactory = ViewModelFactory(authRepository)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        binding.progressBar.visibility = View.INVISIBLE
        binding.btnLogin.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false


        binding.edLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setSubmitEnable()
            }
            override fun afterTextChanged(s: Editable) {

            }

        })

        loginPageButton()
        playAnimation()
    }

    private fun setSubmitEnable() {
        val password = binding.edLoginPassword.text
        binding.btnLogin.isEnabled = password.toString().length >= 8
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.tvLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val submitBtn = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.tvDesc, View.ALPHA, 1f).setDuration(500)
        val registerBtn = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(desc, registerBtn)
        }

        AnimatorSet().apply {
            playSequentially(submitBtn, together)
            start()
        }
    }

    private fun loginPageButton() {
        binding.btnLogin.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.INVISIBLE

            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            loginViewModel.login(email, password)
            loginViewModel.getLoginResult().observe(this, Observer {isSuccess ->
                if (isSuccess) {
                    val token = loginViewModel.getToken(this)
                    if (!token.isNullOrEmpty()) {
                        Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            })



//            RetrofitClient.instance.login(
//                binding.edLoginEmail.text.toString(),
//                binding.edLoginPassword.text.toString()
//            ).enqueue(object : Callback<LoginResponse> {
//                override fun onResponse(
//                    call: Call<LoginResponse>,
//                    response: Response<LoginResponse>
//                ) {
//                    val user = response.body()
//                    if (user?.error == false) {
//                        Log.d("token", user.loginResult.token)
//
//                        val sharedPref = getSharedPreferences("auth", Context.MODE_PRIVATE)
//                        val editor = sharedPref.edit()
//                        editor.putString("token", user.loginResult.token)
//                        editor.apply()
//                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                        finish()
//                    } else {
//                        binding.progressBar.visibility = View.INVISIBLE
//                        binding.btnLogin.visibility = View.VISIBLE
//                        Toast.makeText(baseContext, "Email/Password salah", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    Log.e("error", t.message.toString())
//                }
//            })
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}