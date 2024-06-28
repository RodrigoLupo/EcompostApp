package com.example.ecompost

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.logUsername)
        val passwordEditText = findViewById<EditText>(R.id.logPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val user = User(username, password)
            val apiService = RetrofitClient.instance.create(ApiService::class.java)
            val call = apiService.loginProveedor(user)

            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val tokenResponse = response.body()
                        val accessToken = tokenResponse?.access ?: ""
                        val refreshToken = tokenResponse?.refresh ?: ""

                        PreferenceHelper.saveAccessToken(this@LoginActivity, accessToken)
                        PreferenceHelper.saveRefreshToken(this@LoginActivity, refreshToken)
                        PreferenceHelper.setLoggedIn(this@LoginActivity, true)

                        val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Error en el login", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error en el login: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}