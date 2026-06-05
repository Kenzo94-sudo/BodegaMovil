package com.appbodega.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appbodega.mobile.catalogo_bebidas
import com.appbodega.mobile.catalogo_snacks
import com.google.android.material.button.MaterialButton

class catalogo_bodega : AppCompatActivity() {

    private lateinit var iv_snacks: ImageView
    private lateinit var iv_abarrotes: ImageView
    private lateinit var iv_alcohol: ImageView
    private lateinit var iv_limpieza: ImageView
    private lateinit var ivcatalogo_bebidas: ImageView

    private lateinit var btnSalir : MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_bodega)

        iv_snacks = findViewById(R.id.iv_snacks)
        iv_snacks.setOnClickListener {
            var intent = Intent(this, catalogo_snacks::class.java)
            startActivity(intent)
        }

        ivcatalogo_bebidas = findViewById(R.id.iv_bebidas)
        ivcatalogo_bebidas.setOnClickListener {
            var intent = Intent(this, catalogo_bebidas::class.java)
            startActivity(intent)
        }

        iv_abarrotes = findViewById(R.id.iv_abarrotes)
        iv_abarrotes.setOnClickListener{
            var intent = Intent(this, catalogo_abarrotes::class.java)
            startActivity(intent)
        }

        iv_limpieza = findViewById(R.id.iv_limpieza)
        iv_limpieza.setOnClickListener {
            var intent = Intent(this, catalogo_limpieza::class.java)
            startActivity(intent)
        }

        iv_alcohol = findViewById(R.id.iv_alcohol)
        iv_alcohol.setOnClickListener{
            var intent = Intent(this, catalogo_alcohol::class.java)
            startActivity(intent)
        }

        btnSalir = findViewById(R.id.btnSalir)
        btnSalir.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}