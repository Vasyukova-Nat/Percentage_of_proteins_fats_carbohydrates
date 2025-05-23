package com.example.percentage_of_proteins_fats_carbohydrates

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Калькулятор БЖУ"

        val proteinsInput = findViewById<EditText>(R.id.proteinsInput)
        val fatsInput = findViewById<EditText>(R.id.fatsInput)
        val carbsExpression = findViewById<EditText>(R.id.carbsExpressionInput)
        //val carbsInput = parts.getOrNull(0)?.toIntOrNull() ?: 0
        //val subtractCarbsInput = if (parts.size > 1) parts[1].toIntOrNull() ?: 0 else 0
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        calculateButton.setOnClickListener {
            try {
                val proteins = proteinsInput.text.toString().toInt()
                val fats = fatsInput.text.toString().toInt()
                val expression = carbsExpression.text.toString()
                val parts = expression.split("-")
                val carbs = parts[0].toInt()
                val subtract_carbs = if (parts.size > 1) parts[1].toInt() else 0

                if (proteins < 0 || fats < 0 || carbs < 0) {
                    Toast.makeText(this, "Значения не могут быть отрицательными", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val with_sucrose_total = proteins + fats + carbs
                if (with_sucrose_total == 0) {
                    Toast.makeText(this, "Сумма значений не может быть нулевой", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val with_sucrose_proteins_percent = (proteins * 100.0 / with_sucrose_total).toInt()
                val with_sucrose_fats_percent = (fats * 100.0 / with_sucrose_total).toInt()
                val with_sucrose_carbs_percent = (carbs * 100.0 / with_sucrose_total).toInt()

                val without_sucrose_total = proteins + fats + carbs - subtract_carbs
                val without_sucrose_proteins_percent = (proteins * 100.0 / without_sucrose_total).toInt()
                val without_sucrose_fats_percent = (fats * 100.0 / without_sucrose_total).toInt()
                val without_sucrose_carbs_percent = ((carbs - subtract_carbs) * 100.0 / without_sucrose_total).toInt()

                if (subtract_carbs != 0) {
                    val result =
                        "С сахарозой:\nБелки: $with_sucrose_proteins_percent%\nЖиры: $with_sucrose_fats_percent%\nУглеводы: $with_sucrose_carbs_percent%\n " +
                                "\nПосле вычета сахарозы:\nБелки: $without_sucrose_proteins_percent%\nЖиры: $without_sucrose_fats_percent%\nУглеводы: $without_sucrose_carbs_percent%"
                    resultText.text = result
                } else {
                    val result =
                        "Белки: $without_sucrose_proteins_percent%\nЖиры: $without_sucrose_fats_percent%\nУглеводы: $without_sucrose_carbs_percent%\n" +
                                "\nСахароза не указана. "
                    resultText.text = result
                }

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Пожалуйста, введите все значения", Toast.LENGTH_SHORT).show()
            }
        }
    }
}