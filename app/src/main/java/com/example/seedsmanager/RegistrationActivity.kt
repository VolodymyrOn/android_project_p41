package com.example.seedsmanager


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)
        val et_name = findViewById<EditText>(R.id.editTextName)
        val et_surname = findViewById<EditText>(R.id.editTextSurname)
        val et_email = findViewById<EditText>(R.id.editTextEmail)
        val et_DOB = findViewById<EditText>(R.id.editTextDateOfBirth)
        val et_password = findViewById<EditText>(R.id.editTextPassword)
        val et_cpassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val et_login = findViewById<EditText>(R.id.editTextLogin)
        val button = findViewById<Button>(R.id.buttonSubmit)
        val sharedPreferences = getSharedPreferences(Const.MY_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

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
                val dateFormat = SimpleDateFormat(getString(R.string.dd_mm_yyyy), Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                et_DOB.setText(formattedDate)
            }

            datePickerDialog.show()
        }


        button.setOnClickListener {
            if (et_name.text.isNullOrEmpty() || et_email.text.isNullOrEmpty() ||
                et_DOB.text.isNullOrEmpty() || et_password.text.isNullOrEmpty() ||
                et_cpassword.text.isNullOrEmpty() || et_login.text.isNullOrEmpty()
            ) {
                Toast.makeText(this,
                    getString(R.string.edittext_not_written_error), Toast.LENGTH_SHORT).show()
            } else {
                if (et_cpassword.text.toString() == et_password.text.toString()) {
                    editor.putString(Const.NAME, et_name.text.toString())
                    editor.putString(Const.SURNAME, et_surname.text.toString())
                    editor.putString(Const.EMAIL, et_email.text.toString())
                    editor.putString(Const.DOB, et_DOB.text.toString())
                    editor.putString(Const.LOGIN, et_login.text.toString())
                    editor.putString(Const.PASS, et_password.text.toString())
                    editor.apply()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,
                        getString(R.string.passwords_not_identical_error), Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, et_password.text.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, et_cpassword.text.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        }
    }
 }