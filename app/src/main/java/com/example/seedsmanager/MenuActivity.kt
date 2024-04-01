package com.example.seedsmanager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val button=findViewById<Button>(R.id.button)
        val button2=findViewById<Button>(R.id.button2)
        val buttonAdd=findViewById<Button>(R.id.buttonAdd)
        val sharedPreferences = getSharedPreferences(Const.MY_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        button.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            editor.putString(Const.IS_AUTH, false.toString())
            editor.apply()
            startActivity(intent)
            finish()
        }
    }
}