package com.appbodega.app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.VentaAdapter
import com.appbodega.entity.venta
import com.appbodega.repository.VentaRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class historial_ventas : AppCompatActivity() {

    private lateinit var spinnerMetodo: Spinner
    private lateinit var tvDesde: TextView
    private lateinit var tvHasta: TextView
    private lateinit var tvTotalVentas: TextView
    private lateinit var tvTotalFiltrado: TextView
    private lateinit var btnAtras: MaterialButton
    private lateinit var rvHistorial: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var chipGroup: ChipGroup

    private lateinit var adapter: VentaAdapter
    private var ventaListener: ValueEventListener? = null
    private var todasLasVentas: List<venta> = emptyList()

    private var metodoSeleccionado   = "Todos"
    private var categoriaSeleccionada = "Todas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_ventas)

        spinnerMetodo    = findViewById(R.id.spinnerMetodo)
        tvDesde          = findViewById(R.id.tvDesde)
        tvHasta          = findViewById(R.id.tvHasta)
        tvTotalVentas    = findViewById(R.id.tvTotalVentas)
        tvTotalFiltrado  = findViewById(R.id.tvTotalFiltrado)
        btnAtras         = findViewById(R.id.btnAtras)
        rvHistorial      = findViewById(R.id.rvHistorialVentas)
        tvEmpty          = findViewById(R.id.tvEmptyVentas)
        chipGroup        = findViewById(R.id.chipGroupCategorias)

        adapter = VentaAdapter(emptyList()) { venta ->
            Toast.makeText(this, "Detalle: ${venta.codigo}", Toast.LENGTH_SHORT).show()
            // TODO: abrir dialog boleta
        }
        rvHistorial.layoutManager = LinearLayoutManager(this)
        rvHistorial.adapter = adapter

        cargarMetodos()
        configurarChips()
        cargarDesdeFirebase()

        tvDesde.setOnClickListener { mostrarCalendario(tvDesde) }
        tvHasta.setOnClickListener { mostrarCalendario(tvHasta) }

        spinnerMetodo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>?, v: android.view.View?, pos: Int, id: Long) {
                metodoSeleccionado = spinnerMetodo.selectedItem.toString()
                aplicarFiltros()
            }
            override fun onNothingSelected(p: AdapterView<*>?) {}
        }

        btnAtras.setOnClickListener {
            startActivity(Intent(this, registro_ventas::class.java))
        }
    }

    private fun cargarDesdeFirebase() {
        ventaListener = VentaRepository.escucharTodas(
            onDatos = { lista ->
                runOnUiThread {
                    todasLasVentas = lista
                    tvTotalVentas.text = "S/ %.2f".format(
                        lista.sumOf { it.total }
                    )
                    aplicarFiltros()
                }
            },
            onError = { error ->
                runOnUiThread {
                    Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun aplicarFiltros() {
        var resultado = todasLasVentas

        if (metodoSeleccionado != "Todos")
            resultado = resultado.filter { it.metodo == metodoSeleccionado }

        if (categoriaSeleccionada != "Todas")
            resultado = resultado.filter { it.categoria == categoriaSeleccionada }

        tvTotalFiltrado.text = "S/ %.2f".format(resultado.sumOf { it.total })

        if (resultado.isEmpty()) {
            tvEmpty.visibility = android.view.View.VISIBLE
            rvHistorial.visibility = android.view.View.GONE
        } else {
            tvEmpty.visibility = android.view.View.GONE
            rvHistorial.visibility = android.view.View.VISIBLE
        }

        adapter.actualizar(resultado)
    }

    private fun configurarChips() {
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            categoriaSeleccionada = when {
                checkedIds.contains(R.id.chipAbarrotes) -> "Abarrotes"
                checkedIds.contains(R.id.chipBebidas)   -> "Bebidas"
                checkedIds.contains(R.id.chipLimpieza)  -> "Limpieza"
                checkedIds.contains(R.id.chipSnacks)    -> "Snacks"
                checkedIds.contains(R.id.chipAlcohol)   -> "Alcohol"
                else                                    -> "Todas"
            }
            aplicarFiltros()
        }
    }

    private fun cargarMetodos() {
        val metodos = arrayOf("Todos", "Efectivo", "Yape")
        spinnerMetodo.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, metodos
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    private fun mostrarCalendario(tv: TextView) {
        val cal = Calendar.getInstance()
        DatePickerDialog(this, { _, y, m, d ->
            tv.text = "%02d/%02d/%04d".format(d, m + 1, y)
            validarFechas()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun validarFechas() {
        if (tvDesde.text.isNotEmpty() && tvHasta.text.isNotEmpty()) {
            val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val desde = fmt.parse(tvDesde.text.toString())
            val hasta = fmt.parse(tvHasta.text.toString())
            if (desde != null && hasta != null && desde.after(hasta)) {
                Toast.makeText(this, "La fecha inicial no puede ser mayor que la final", Toast.LENGTH_SHORT).show()
                tvHasta.text = tvDesde.text
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ventaListener?.let {
            VentaRepository.ref.removeEventListener(it)
        }
    }
}