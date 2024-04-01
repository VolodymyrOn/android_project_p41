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
        val sharedPreferences = getSharedPreferences(Const.MY_PREF, Context.MODE_PRIVATE)
        val login = sharedPreferences.getString(Const.LOGIN, "")
        val password = sharedPreferences.getString(Const.PASS, "")
        val editor = sharedPreferences.edit()
        reg.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
        if(sharedPreferences.getString(Const.IS_AUTH, "")==true.toString()){
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth.setOnClickListener{
            if(et1.text.toString()==""||et2.text.toString()==""){
                Toast.makeText(this,
                    getString(R.string.String_must_be_written_error), Toast.LENGTH_SHORT).show()
            }
            else if(login==et1.text.toString()&&password==et2.text.toString()){
                val intent = Intent(this, MenuActivity::class.java)
                editor.putString(Const.IS_AUTH, true.toString())
                editor.apply()
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,
                    getString(R.string.login_or_password_wrong_error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}