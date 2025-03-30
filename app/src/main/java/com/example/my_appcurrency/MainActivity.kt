package com.example.my_appcurrency

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etAmount = findViewById<EditText>(R.id.etAmount)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val currencies = listOf("USD", "VND", "EUR", "JPY", "GBP")
        val exchangeRates = mapOf(
            "USD" to 1.0,
            "VND" to 23185.0,
            "EUR" to 0.85,
            "JPY" to 110.5,
            "GBP" to 0.73
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        btnConvert.setOnClickListener {
            val amountText = etAmount.text.toString().trim()
            if (amountText.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromCurrency = spinnerFrom.selectedItem.toString()
            val toCurrency = spinnerTo.selectedItem.toString()

            if (fromCurrency == toCurrency) {
                tvResult.text = "Result: %.2f %s".format(amount, toCurrency)
                return@setOnClickListener
            }

            val rateFrom = exchangeRates[fromCurrency] ?: 1.0
            val rateTo = exchangeRates[toCurrency] ?: 1.0
            val result = (amount / rateFrom) * rateTo

            tvResult.text = "Result: %.2f %s".format(result, toCurrency)
        }
    }
}
