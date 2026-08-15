package com.appbodega.app

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appbodega.entity.Producto
import com.appbodega.entity.venta
import com.appbodega.repository.VentaRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class registro_ventas : AppCompatActivity() {

    private lateinit var toolbarVentas: MaterialToolbar
    private lateinit var tvCodigoGenerado: TextView
    private lateinit var btnAbrirCatalogoCategorias: MaterialButton
    private lateinit var btnEscanearVenta: MaterialButton
    private lateinit var spinnerCategoriasVenta: Spinner
    private lateinit var spinnerProductos: Spinner
    private lateinit var layoutInfoProducto: LinearLayout
    private lateinit var imgProductoPreview: android.widget.ImageView
    private lateinit var tvNombreProductoSeleccionado: TextView
    private lateinit var tvCategoriaProductoSeleccionado: TextView
    private lateinit var tvStockProductoSeleccionado: TextView
    private lateinit var txtPrecioUnitario: TextView
    private lateinit var btnMenosCantidad: ImageButton
    private lateinit var txtCantidad: EditText
    private lateinit var btnMasCantidad: ImageButton
    private lateinit var spinnerMetodoPago: Spinner
    private lateinit var txtFechaVenta: TextView
    private lateinit var txtSubtotalCalculado: TextView
    private lateinit var txtIgvCalculado: TextView
    private lateinit var txtTotalCalculado: TextView
    private lateinit var btnRegistrarVenta: MaterialButton
    private lateinit var btnHistorialVentas: MaterialButton

    private val listaProductos = mutableListOf<Producto>()
    private var productosFiltrados = mutableListOf<Producto>()
    private var productoSeleccionado: Producto? = null

    private val scannerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val codigo = result.data?.getStringExtra("codigo_escaneado")
                if (!codigo.isNullOrEmpty()) {
                    seleccionarProductoPorCodigo(codigo)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_ventas)

        toolbarVentas              = findViewById(R.id.toolbarVentas)
        tvCodigoGenerado           = findViewById(R.id.tvCodigoGenerado)
        btnAbrirCatalogoCategorias = findViewById(R.id.btnAbrirCatalogoCategorias)
        btnEscanearVenta           = findViewById(R.id.btnEscanearVenta)
        spinnerCategoriasVenta     = findViewById(R.id.spinnerCategoriasVenta)
        spinnerProductos           = findViewById(R.id.spinnerProductos)
        layoutInfoProducto         = findViewById(R.id.layoutInfoProducto)
        imgProductoPreview         = findViewById(R.id.imgProductoPreview)
        tvNombreProductoSeleccionado   = findViewById(R.id.tvNombreProductoSeleccionado)
        tvCategoriaProductoSeleccionado = findViewById(R.id.tvCategoriaProductoSeleccionado)
        tvStockProductoSeleccionado    = findViewById(R.id.tvStockProductoSeleccionado)
        txtPrecioUnitario          = findViewById(R.id.txtPrecioUnitario)
        btnMenosCantidad           = findViewById(R.id.btnMenosCantidad)
        txtCantidad                = findViewById(R.id.txtCantidad)
        btnMasCantidad             = findViewById(R.id.btnMasCantidad)
        spinnerMetodoPago          = findViewById(R.id.spinnerMetodoPago)
        txtFechaVenta              = findViewById(R.id.txtFechaVenta)
        txtSubtotalCalculado       = findViewById(R.id.txtSubtotalCalculado)
        txtIgvCalculado            = findViewById(R.id.txtIgvCalculado)
        txtTotalCalculado          = findViewById(R.id.txtTotalCalculado)
        btnRegistrarVenta          = findViewById(R.id.btnRegistrarVenta)
        btnHistorialVentas         = findViewById(R.id.btnHistorialVentas)

        toolbarVentas.setNavigationOnClickListener { finish() }

        txtFechaVenta.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        spinnerMetodoPago.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("Efectivo", "Yape")
        )

        spinnerCategoriasVenta.adapter = ArrayAdapter.createFromResource(
            this, R.array.lista_categorias,
            android.R.layout.simple_spinner_dropdown_item
        )

        layoutInfoProducto.visibility = android.view.View.GONE
        recalcularTotales()

        cargarProductosDesdeFirebase()

        spinnerCategoriasVenta.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p: AdapterView<*>?, v: android.view.View?, pos: Int, id: Long) {
                    filtrarProductosPorCategoria(p?.getItemAtPosition(pos).toString())
                }
                override fun onNothingSelected(p: AdapterView<*>?) {}
            }

        spinnerProductos.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p: AdapterView<*>?, v: android.view.View?, pos: Int, id: Long) {
                    if (pos in productosFiltrados.indices) {
                        mostrarProductoSeleccionado(productosFiltrados[pos])
                    }
                }
                override fun onNothingSelected(p: AdapterView<*>?) {}
            }

        btnAbrirCatalogoCategorias.setOnClickListener {
            spinnerCategoriasVenta.performClick()
        }

        btnEscanearVenta.setOnClickListener {
            val intent = Intent(this, RegistroProductoActivity::class.java)
            scannerLauncher.launch(intent)
        }

        btnMenosCantidad.setOnClickListener {
            val actual = txtCantidad.text.toString().toIntOrNull() ?: 1
            if (actual > 1) txtCantidad.setText((actual - 1).toString())
        }

        btnMasCantidad.setOnClickListener {
            val actual = txtCantidad.text.toString().toIntOrNull() ?: 1
            val stock = productoSeleccionado?.cantidad ?: 0
            if (actual < stock) {
                txtCantidad.setText((actual + 1).toString())
            } else {
                Toast.makeText(this, "Sin stock disponible", Toast.LENGTH_SHORT).show()
            }
        }

        txtCantidad.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { recalcularTotales() }
        })

        btnRegistrarVenta.setOnClickListener { registrarVenta() }

        btnHistorialVentas.setOnClickListener {
            startActivity(Intent(this, historial_ventas::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarProductosDesdeFirebase() {
        FirebaseDatabase.getInstance().getReference("productos")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listaProductos.clear()
                    for (item in snapshot.children) {
                        item.getValue(Producto::class.java)?.let { listaProductos.add(it) }
                    }
                    filtrarProductosPorCategoria(
                        spinnerCategoriasVenta.selectedItem?.toString() ?: ""
                    )
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun filtrarProductosPorCategoria(categoria: String) {
        productosFiltrados = listaProductos
            .filter { it.categoria.equals(categoria, ignoreCase = true) }
            .toMutableList()

        spinnerProductos.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            productosFiltrados.map { it.nombre }
        )

        if (productosFiltrados.isEmpty()) {
            layoutInfoProducto.visibility = android.view.View.GONE
            productoSeleccionado = null
            recalcularTotales()
        }
    }

    private fun seleccionarProductoPorCodigo(codigo: String) {
        val producto = listaProductos.find { it.codigoBarras == codigo }
        if (producto == null) {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
            return
        }
        filtrarProductosPorCategoria(producto.categoria)
        val idx = productosFiltrados.indexOfFirst { it.id == producto.id }
        if (idx >= 0) spinnerProductos.setSelection(idx)
        mostrarProductoSeleccionado(producto)
    }

    private fun mostrarProductoSeleccionado(producto: Producto) {
        productoSeleccionado = producto
        layoutInfoProducto.visibility = android.view.View.VISIBLE

        tvNombreProductoSeleccionado.text    = producto.nombre
        tvCategoriaProductoSeleccionado.text = "Categoría: ${producto.categoria}"
        tvStockProductoSeleccionado.text     = "Stock: ${producto.cantidad}"
        txtPrecioUnitario.text               = "P. Unit: S/ %.2f".format(producto.precioVenta)

        if (producto.imagenBase64.isNotEmpty()) {
            try {
                val bytes = Base64.decode(producto.imagenBase64, Base64.DEFAULT)
                imgProductoPreview.setImageBitmap(
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                )
            } catch (e: Exception) {
                imgProductoPreview.setImageResource(R.drawable.icono_camara)
            }
        } else {
            imgProductoPreview.setImageResource(R.drawable.icono_camara)
        }

        txtCantidad.setText("1")
        recalcularTotales()
    }

    private fun recalcularTotales() {
        val precioVenta = productoSeleccionado?.precioVenta ?: 0.0
        val cantidad    = txtCantidad.text.toString().toIntOrNull() ?: 0

        // El precio de venta YA incluye IGV — se extrae, no se agrega
        val totalConIgv = precioVenta * cantidad
        val igv         = totalConIgv - (totalConIgv / 1.18)
        val subtotal    = totalConIgv - igv

        txtSubtotalCalculado.text = "S/ %.2f".format(subtotal)
        txtIgvCalculado.text      = "S/ %.2f".format(igv)
        txtTotalCalculado.text    = "S/ %.2f".format(totalConIgv)
    }

    private fun registrarVenta() {
        val producto = productoSeleccionado ?: run {
            Toast.makeText(this, "Selecciona un producto primero", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = txtCantidad.text.toString().toIntOrNull() ?: 0

        if (cantidad <= 0) {
            Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show()
            return
        }

        if (cantidad > producto.cantidad) {
            Toast.makeText(this, "Stock insuficiente (disponible: ${producto.cantidad})", Toast.LENGTH_SHORT).show()
            return
        }

        val metodoPago  = spinnerMetodoPago.selectedItem?.toString() ?: "Efectivo"
        val totalConIgv = (producto.precioVenta * cantidad)

        val nuevaVenta = venta(
            fecha       = txtFechaVenta.text.toString(),
            cantidad    = cantidad,
            metodo      = metodoPago,
            categoria   = producto.categoria,
            total       = totalConIgv,
            productoId  = producto.id
        )

        btnRegistrarVenta.isEnabled = false

        VentaRepository.registrar(
            nuevaVenta,
            onExito = { codigoGenerado ->
                FirebaseDatabase.getInstance()
                    .getReference("productos")
                    .child(producto.id)
                    .child("cantidad")
                    .setValue(producto.cantidad - cantidad)

                tvCodigoGenerado.text = "Código: $codigoGenerado"
                Toast.makeText(this, "Venta $codigoGenerado registrada ✓", Toast.LENGTH_SHORT).show()
                btnRegistrarVenta.isEnabled = true
                limpiarFormulario()
            },
            onError = { mensaje ->
                btnRegistrarVenta.isEnabled = true
                Toast.makeText(this, "Error: $mensaje", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun limpiarFormulario() {
        productoSeleccionado = null
        layoutInfoProducto.visibility = android.view.View.GONE
        txtCantidad.setText("1")
        recalcularTotales()
    }
}