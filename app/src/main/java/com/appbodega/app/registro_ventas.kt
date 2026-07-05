package com.appbodega.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appbodega.ui.CategoriasFragment
import com.google.android.material.button.MaterialButton

class registro_ventas : AppCompatActivity() {
    private lateinit var btnAtras: MaterialButton
    private lateinit var btnHistorialVentas: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_ventas)
        btnAtras=findViewById(R.id.btnAtras)


        btnAtras.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flayContenedor, CategoriasFragment())
                .commit()
        }

        btnHistorialVentas = findViewById(R.id.btnHistorialVentas)
        btnHistorialVentas.setOnClickListener {
            val intent = Intent(this, historial_ventas::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}