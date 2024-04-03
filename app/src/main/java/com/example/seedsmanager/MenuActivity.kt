package com.example.seedsmanager

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val button=findViewById<Button>(R.id.button)
        val buttonInfo=findViewById<Button>(R.id.buttonInfo)
        val IVProfile=findViewById<ImageView>(R.id.imageView)
        val buttonManager=findViewById<Button>(R.id.buttonManger)
        val textname=findViewById<TextView>(R.id.textViewName)
        val textsurname=findViewById<TextView>(R.id.textViewSurname)
        val textDOB=findViewById<TextView>(R.id.textViewDOB)
        val sharedPreferences = getSharedPreferences(Const.MY_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val name=sharedPreferences.getString(Const.NAME, "")
        val surname=sharedPreferences.getString(Const.SURNAME, "")
        val dob=sharedPreferences.getString(Const.DOB, "")

        val encodedImage = sharedPreferences.getString(Const.IMAGE, "")
        if (!encodedImage.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            IVProfile.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    decodedString,
                    0,
                    decodedString.size
                )
            )}

        textname.text=name
        textsurname.text=surname
        textDOB.text=dob

        button.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            editor.putString(Const.IS_AUTH, false.toString())
            editor.apply()
            startActivity(intent)
            finish()
        }

        buttonInfo.setOnClickListener{
            val intent = Intent(this, SeedsInfoActivity::class.java)
            startActivity(intent)
        }

        buttonManager.setOnClickListener{
            val intent = Intent(this, SeedsManagerActivity::class.java)
            startActivity(intent)
        }

        IVProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}