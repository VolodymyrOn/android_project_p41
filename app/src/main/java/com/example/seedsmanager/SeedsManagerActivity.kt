package com.example.seedsmanager

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeedsManagerActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ManagerAdapter
    private val dataSet = arrayListOf<ManagerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeds_manager)

        val add=findViewById<Button>(R.id.button_add)
        val finish=findViewById<Button>(R.id.button_finish)

        finish.setOnClickListener{
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ManagerAdapter(dataSet){position->
            showUpdateDialog(position = position, managerModel = dataSet[position] )
        }
        recyclerView.adapter = adapter

        dataSet.addAll(arrayListOf(
            ManagerModel("Часник", "Листопад", 2 ),
            ManagerModel("Цибуля", "Листопад", 5 ),
            ManagerModel("Морква", "Березень", 3 ),
            ManagerModel("Буряк", "Березень", 4 ),
            ManagerModel("Горох", "Березень", 2 ),
            ManagerModel("Петрушка", "Березень", 1 ),
            ManagerModel("Картопля", "Березень", 10 ),
            ManagerModel("Салат", "Березень", 2 ),
            ManagerModel("Редька", "Квітень", 2 ),
            ManagerModel("Кукурудза", "Квітень", 5 ),


        ))
        add.setOnClickListener(){
            showAddTeacherDialog()
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showAddTeacherDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_manager, null)
        val sEditText = dialogView.findViewById<EditText>(R.id.surnameEditText)
        val nEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
        val pEditText = dialogView.findViewById<EditText>(R.id.patronimicEditText)

        builder.setView(dialogView)
            .setTitle("Додати посаджене насіння")
            .setPositiveButton("Додати") { dialog, which ->
                // Отримуємо дані з полів вводу та додаємо новий елемент у список
                val whenMonth = sEditText.text.toString()
                val name = nEditText.text.toString()
                val howMany = pEditText.text.toString().toInt()


                dataSet.add(ManagerModel(name, whenMonth, howMany))
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("Скасувати") { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    private fun showUpdateDialog(position: Int, managerModel: ManagerModel) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_manager, null)

        val sEditText = dialogView.findViewById<EditText>(R.id.surnameEditText)
        val nEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
        val pEditText = dialogView.findViewById<EditText>(R.id.patronimicEditText)

        // Встановлюємо існуючі значення в поля для вводу
        sEditText.setText(managerModel.whenMonth)
        nEditText.setText(managerModel.name)
        pEditText.setText(managerModel.howMany)

        builder.setView(dialogView)
            .setTitle("Редагувати")
            .setPositiveButton("Змінити") { dialog, which ->
                // Отримуємо оновлені дані з полів вводу
                val whenMonth = sEditText.text.toString()
                val name = nEditText.text.toString()
                val howMany = pEditText.text.toString().toInt()

                // Оновлюємо елемент у списку
                val updatedTeacher = ManagerModel(name, whenMonth, howMany)
                dataSet[position] = updatedTeacher
                adapter.notifyItemChanged(position)
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Відміна оновлення
                dialog.cancel()
            }
            .show()
    }

}