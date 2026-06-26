package com.appbodega.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class catalogo_abarrotes : AppCompatActivity() {
//Aqui se definen las varaiables que utilizare del diseño a la programacion
    private lateinit var btnAtras: MaterialButton
    private lateinit var btnRegistroAbarrotes: MaterialButton
    private lateinit var imgCerrar: ImageView
    private lateinit var rvSnack: RecyclerView
    private lateinit var adaptador: ProductoAdapter

    // Esta es la lista donde se guardaran temporalmente los datos agrupados de los productos
    private val listaProductos = ArrayList<Producto>()

    // En estea apartado el
    private val lanzadorRegistro = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        // Si el usuario guaroo el producto con exito
        if (resultado.resultCode == RESULT_OK) {
            val datos: Intent? = resultado.data
            //Aqui abre el sobre y extrae el paquete con la etiqueta nuevo producto
            val productoRecibido = datos?.getSerializableExtra("NUEVO_PRODUCTO") as? Producto
            if (productoRecibido != null) {
                listaProductos.add(productoRecibido) //Guarda el producto en la lista  que es RAM
                adaptador.notifyItemInserted(listaProductos.size - 1) // Refresca la lista en pantalla
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo_abarrotes) // Vincula tu XML limpio

        // Inicializamos los componentes del catálogo
        btnAtras = findViewById(R.id.btnAtras)
        imgCerrar = findViewById(R.id.img_cerrar)
        btnRegistroAbarrotes = findViewById(R.id.btn_registro_abarrotes)
        rvSnack = findViewById(R.id.rv_snack)

        // Configuración básica del RecyclerView
        rvSnack.layoutManager = LinearLayoutManager(this) // Lista vertical
        adaptador = ProductoAdapter(listaProductos) // Le asignamos nuestra lista
        rvSnack.adapter = adaptador

        // Botón Atrás: Vuelve al menú anterior
        btnAtras.setOnClickListener {
            val intent = Intent(this, catalogo_bodega::class.java)
            startActivity(intent)
        }

        // Icono cerrar: Vuelve al menú anterior
        imgCerrar.setOnClickListener {
            val intent = Intent(this, catalogo_bodega::class.java)
            startActivity(intent)
        }

        // Botón Registrar: Lanza la pantalla de registro esperando una respuesta
        btnRegistroAbarrotes.setOnClickListener {
            val intent = Intent(this, registrar_productos::class.java)
            lanzadorRegistro.launch(intent) // Abre la pantalla de registro usando el escuchador
        }
    }
}