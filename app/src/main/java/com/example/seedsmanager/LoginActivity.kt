package com.example.seedsmanager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val reg=findViewById<Button>(R.id.button_reg)
        val auth=findViewById<Button>(R.id.button_auth)
        val et1=findViewById<EditText>(R.id.editTextText)
        val et2=findViewById<EditText>(R.id.editTextTextPassword)
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val login = sharedPreferences.getString("Login", "")
        val password = sharedPreferences.getString("Password", "")

        reg.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth.setOnClickListener{
            if(et1.text.toString()==""||et2.text.toString()==""){
                Toast.makeText(this, "Поля повинні бути заповнені", Toast.LENGTH_SHORT).show()
            }
            else if(login==et1.text.toString()&&password==et2.text.toString()){
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Не вірно введено логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}