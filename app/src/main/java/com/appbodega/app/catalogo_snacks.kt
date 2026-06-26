package com.appbodega.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class catalogo_snacks : AppCompatActivity() {
    private lateinit var  btnAtras: MaterialButton
    private lateinit var img_cerrar: ImageView
    private lateinit var rvSnack: RecyclerView
    private lateinit var btn_registro_snacks: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_snacks)

        btnAtras=findViewById(R.id.btnAtras)
        btnAtras.setOnClickListener {
            var intent= Intent(this, catalogo_bodega::class.java)
            startActivity(intent)
        }
        btn_registro_snacks = findViewById(R.id.btn_registro_snacks)
        btn_registro_snacks.setOnClickListener {
            var intent = Intent(this, registrar_productos::class.java)
            startActivity(intent)
        }

        img_cerrar = findViewById(R.id.img_cerrar)
        img_cerrar.setOnClickListener {
            var intent = Intent(this, catalogo_bodega::class.java)
            startActivity(intent)
        }

        rvSnack = findViewById(R.id.rv_snack)
        rvSnack.layoutManager = LinearLayoutManager(this)


        val listaSnacks = listOf(
            Producto(
                nombre = "Doritos",
                descripcion = "Tortilla de mazi con sabor a queso",
                cantidad = 10,
                precio = 2.50,
                imagenResId = R.drawable.doritos1
            ),
            Producto(
                nombre = "Inka Chips",
                descripcion = "Snack de papas",
                cantidad = 8,
                precio = 3.20,
                imagenResId = R.drawable.inkachips
            ),
            Producto(
                nombre = "Picaras",
                descripcion = "Galleta bañada en chocolate",
                cantidad = 5,
                precio = 2.50,
                imagenResId = R.drawable.picaras
            )
        )

        rvSnack.adapter = ProductoAdapter(listaSnacks)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}