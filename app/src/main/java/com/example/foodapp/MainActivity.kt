package com.example.foodapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.foodapp.data.User
import com.example.foodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var users = ArrayList<User>()
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialSetup()
        loginListener()
        checkCredentials(this)
    }

    fun checkCredentials(context: Context): Unit {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val uname = sharedPreferences.getString("username", null)
        val pwd = sharedPreferences.getString("password", null)

        if(uname != null && pwd != null) {
            users.forEach{
                if(it.username == uname && it.password == pwd) {
                    gotoHome(it)
                }
            }
        }
    }

    private fun saveCredentials(user: User, context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("username", user.username)
        editor.putString("password", user.password)

        editor.apply()
    }

    private fun initialSetup() {
        username = binding.username
        password = binding.password

        val usersArray = resources.obtainTypedArray(R.array.users)

        for (i in 0 until usersArray.length()) {
            val userArrayResourceId = usersArray.getResourceId(i, 0)
            val user = resources.getStringArray(userArrayResourceId)
            users.add(User(user[0], user[1], user[2]))
        }
    }

    private fun loginListener() {
        binding.signIn.setOnClickListener{
            val uname: String = username.text.toString().trim()
            val pwd: String = password.text.toString().trim()
            users.forEach{
                if(it.username == uname && it.password == pwd) {
                    saveCredentials(it, this)
                    gotoHome(it)
                }
            }
        }
    }

    private fun gotoHome(user: User) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}