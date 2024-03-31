package com.example.seedsmanager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


@Suppress("DEPRECATION")
class RegistrationActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var ImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)
        val et_name = findViewById<EditText>(R.id.editTextName)
        val et_phone = findViewById<EditText>(R.id.editTextPhone)
        val et_DOB = findViewById<EditText>(R.id.editTextDateOfBirth)
        val et_password = findViewById<EditText>(R.id.editTextPassword)
        val et_cpassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val et_login = findViewById<EditText>(R.id.editTextLogin)
        val button = findViewById<Button>(R.id.buttonSubmit)
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        ImageView = findViewById<ImageView>(R.id.imageView)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Запит дозволу на використання камери
            val CAMERA_PERMISSION_REQUEST_CODE = 0
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE
            )
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Запит дозволу на читання зовнішнього сховища
            val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
            )
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Запит дозволу на читання зовнішнього сховища
            val W_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 0
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                W_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
            )
        }

        button.setOnClickListener() {
            if (et_name.text.isNullOrEmpty() || et_phone.text.isNullOrEmpty() ||
                et_DOB.text.isNullOrEmpty() || et_password.text.isNullOrEmpty() ||
                et_cpassword.text.isNullOrEmpty() || et_login.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "Ви не ввели потрібні дані", Toast.LENGTH_SHORT).show()
            } else {
                if (et_cpassword.text.toString() == et_password.text.toString()) {
                    editor.putString("Name", et_name.text.toString())
                    editor.putString("Phone", et_phone.text.toString())
                    editor.putString("DOB", et_DOB.text.toString())
                    editor.putString("Login", et_login.text.toString())
                    editor.putString("Password", et_password.text.toString())
                    editor.apply()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Пароль не співпадає", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, et_password.text.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, et_cpassword.text.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        }
        val REQUEST_IMAGE_GALLERY = 1001
        fun selectImageFromGallery(view: View) {

            val CAMERA_PERMISSION_REQUEST_CODE = 100
            val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101


            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        }

        ImageView.setOnClickListener() {
            val CAMERA_PERMISSION_REQUEST_CODE = 100
            val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101
            //val galleryIntent = Intent(
            //    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            dispatchTakePictureIntent()
            // startActivityForResult (galleryIntent, PICK_IMAGE_REQUEST)
        }



    }
    private val REQUEST_IMAGE_CAPTURE = 1
    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            ImageView.setImageBitmap(bitmap)
        }
    }}