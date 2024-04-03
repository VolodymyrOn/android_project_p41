package com.example.seedsmanager

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.util.Base64
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {
    private lateinit var IV : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val et_name = findViewById<EditText>(R.id.editTextName)
        val et_surname = findViewById<EditText>(R.id.editTextSurname)
        val et_email = findViewById<EditText>(R.id.editTextEmail)
        val et_DOB = findViewById<EditText>(R.id.editTextDateOfBirth)
        val button = findViewById<Button>(R.id.buttonSubmit)
        val sharedPreferences = getSharedPreferences(Const.MY_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        IV = findViewById(R.id.imageView)
        val encodedImage = sharedPreferences.getString(Const.IMAGE, "")
        if (!encodedImage.isNullOrEmpty()) {
            val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            IV.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    decodedString,
                    0,
                    decodedString.size
                )
            )}

        et_DOB.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, null, year, month, day)


                datePickerDialog.datePicker.maxDate = calendar.timeInMillis

                datePickerDialog.setOnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    val dateFormat =
                        SimpleDateFormat(getString(R.string.dd_mm_yyyy), Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)

                    et_DOB.setText(formattedDate)
                }

                datePickerDialog.show()
            }
            IV.setOnClickListener {
                selectImage()
                //editor.putString("Image", I)
                //editor.apply()
            }

            et_name.setText(sharedPreferences.getString(Const.NAME, ""))
            et_surname.setText(sharedPreferences.getString(Const.SURNAME, ""))
            et_email.setText(sharedPreferences.getString(Const.EMAIL, ""))
            et_DOB.setText(sharedPreferences.getString(Const.DOB, ""))

            button.setOnClickListener {
                if (et_name.text.isNullOrEmpty() || et_email.text.isNullOrEmpty() ||
                    et_DOB.text.isNullOrEmpty()
                ) {
                    Toast.makeText(
                        this,
                        getString(R.string.edittext_not_written_error), Toast.LENGTH_SHORT
                    ).show()
                } else {

                    editor.putString(Const.NAME, et_name.text.toString())
                    editor.putString(Const.SURNAME, et_surname.text.toString())
                    editor.putString(Const.EMAIL, et_email.text.toString())
                    editor.putString(Const.DOB, et_DOB.text.toString())
                    editor.apply()
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        private val PICK_IMAGE_REQUEST = 1
        private val REQUEST_IMAGE_CAPTURE = 100
        private val CAMERA_PERMISSION_REQUEST_CODE = 100
        private val STORAGE_PERMISSION_REQUEST_CODE = 101

        private fun checkCameraPermission() {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )

            }
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_REQUEST_CODE
                )
            }
        }

            @SuppressLint("QueryPermissionsNeeded")
            fun selectImage() {
                checkCameraPermission()
                val options = arrayOf<CharSequence>(getString(R.string.take_photo_),
                    getString(R.string.choose_from_gallery_), getString(R.string.cancel_))
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.add_photo))
                builder.setItems(options) { dialog, item ->
                    when {
                        options[item] == getString(R.string.take_photo) -> {
                            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            if (takePictureIntent.resolveActivity(packageManager) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                            }
                        }

                        options[item] == getString(R.string.choose_from_gallery) -> {
                            val intent = Intent()
                            intent.type = "image/*"
                            intent.action = Intent.ACTION_GET_CONTENT
                            startActivityForResult(
                                Intent.createChooser(intent, getString(R.string.select_picture)),
                                PICK_IMAGE_REQUEST
                            )
                        }

                        options[item] == getString(R.string.cancel) -> {
                            dialog.dismiss()
                        }
                    }
                }
                builder.show()
            }

            @SuppressLint("CommitPrefEdits")
            fun saveImageToSharedPreferences(context: Context, bitmap: Bitmap) {
                val editor = context.getSharedPreferences(Const.MY_PREF, Context.MODE_PRIVATE).edit()
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                val encodedImage: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                editor.putString(Const.IMAGE, encodedImage)
                editor.apply()
            }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(getString(R.string.images), Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                val selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                        val filePath = saveImageToInternalStorage(bitmap)
                        saveImageToSharedPreferences(this, bitmap)
                        IV.setImageBitmap(BitmapFactory.decodeFile(filePath))
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                saveImageToSharedPreferences(this, imageBitmap)
                IV.setImageBitmap(imageBitmap)
            }
        }
    }


}
