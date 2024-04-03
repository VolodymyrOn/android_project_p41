package com.example.seedsmanager

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeedsInfoActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SeedAdapter
    private val dataSet = arrayListOf<SeedModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeds_info)

        val finish=findViewById<Button>(R.id.button_finish)

        finish.setOnClickListener{
            finish()
        }

        recyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = SeedAdapter(dataSet){position->
            showUpdateDialog(position = position, seedModel = dataSet[position] )
        }
        recyclerView.adapter = adapter

        dataSet.addAll(arrayListOf(
            SeedModel("Часник", "Жовтень", "Грудень", "9 місяців"),
            SeedModel("Цибуля", "Березень", "Травень", "5 місяців"),
            SeedModel("Горох", "Березень", "Червень", "4 місяці"),
            SeedModel("Петрушка", "Березень", "Липень", "3 місяці"),
            SeedModel("Картопля", "Березень", "Квітень", "7 місяців"),
            SeedModel("Огірок", "Квітень", "Травень", "3 місяці"),
            SeedModel("Помідор", "Квітень", "Травень", "3 місяців"),
            SeedModel("Морква", "Березень", "Травень", "5 місяців"),
            SeedModel("Буряк", "Березень", "Травень", "5 місяців"),
            SeedModel("Редька", "Березень", "Травень", "3 місяців")
            ))


    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")


    private fun showUpdateDialog(position: Int, seedModel: SeedModel) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.seed_info_dialog, null)

        val start = dialogView.findViewById<TextView>(R.id.start_month)
        val end = dialogView.findViewById<TextView>(R.id.end_month)
        val time = dialogView.findViewById<TextView>(R.id.time)

        start.text="Час саджання:"
        end.text=seedModel.startMonth+"-"+seedModel.endMonth
        time.text="Час на зростання: "+seedModel.timeToGrow

        builder.setView(dialogView)
            .setTitle(seedModel.name)
            .setNegativeButton("Назад") { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

}
