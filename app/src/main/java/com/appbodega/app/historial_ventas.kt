package com.appbodega.app

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class historial_ventas : AppCompatActivity() {

    private lateinit var spinnerMetodo: Spinner
    private lateinit var tvDesde: TextView
    private lateinit var tvHasta: TextView
    private lateinit var tvTotalVentas: TextView
    private lateinit var tvTotalFiltrado: TextView

    val ventas = listOf(
        venta("V001", "01/05/2025", 2, "Efectivo", "Abarrotes", 25.0),
        venta("V002", "02/05/2025", 1, "Yape", "Bebidas", 15.0),
        venta("V003", "03/05/2025", 4, "Tarjeta", "Limpieza", 40.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_ventas)

        spinnerMetodo = findViewById(R.id.spinnerMetodo)
        tvDesde = findViewById(R.id.tvDesde)
        tvHasta = findViewById(R.id.tvHasta)
        tvTotalVentas = findViewById(R.id.tvTotalVentas)
        tvTotalFiltrado = findViewById(R.id.tvTotalFiltrado)

        cargarMetodos()

        tvDesde.setOnClickListener {
            mostrarCalendario(tvDesde)
        }
        tvHasta.setOnClickListener {
            mostrarCalendario(tvHasta)
        }

        spinnerMetodo.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {

                    when (spinnerMetodo.selectedItem.toString()) {

                        "Todos" -> {
                            tvTotalFiltrado.text = "S/ 800.00"
                        }

                        "Efectivo" -> {
                            tvTotalFiltrado.text = "S/ 500.00"
                        }

                        "Yape" -> {
                            tvTotalFiltrado.text = "S/ 300.00"
                        }


                    }
                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                }
            }

        tvTotalVentas.text = "S/ 800.00"
        tvTotalFiltrado.text = "S/ 800.00"
    }

    private fun cargarMetodos() {

        val metodos = arrayOf(
            "Todos",
            "Efectivo",
            "Yape"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            metodos
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerMetodo.adapter = adapter
    }

    private fun mostrarCalendario(textView: TextView) {

        val calendario = Calendar.getInstance()

        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val day = calendario.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            { _, y, m, d ->

                textView.text = "%02d/%02d/%04d".format(d, m + 1, y)

                validarFechas()
            },
            year,
            month,
            day
        ).show()
    }
    private fun validarFechas() {

        if (tvDesde.text.isNotEmpty() && tvHasta.text.isNotEmpty()) {

            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val fechaDesde = formato.parse(tvDesde.text.toString())
            val fechaHasta = formato.parse(tvHasta.text.toString())

            if (fechaDesde != null && fechaHasta != null) {

                if (fechaDesde.after(fechaHasta)) {

                    Toast.makeText(
                        this,
                        "La fecha inicial no puede ser mayor que la fecha final",
                        Toast.LENGTH_SHORT
                    ).show()

                    tvHasta.text = tvDesde.text
                }
            }
        }
    }
}