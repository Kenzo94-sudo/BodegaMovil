package com.appbodega.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class catalogo_abarrotes : AppCompatActivity() {
    private lateinit var btnAtras: MaterialButton
    private lateinit var img_cerrar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_abarrotes)
        btnAtras=findViewById(R.id.btnAtras)
        btnAtras.setOnClickListener {
            var intent = Intent(this, catalogo_bodega::class.java)
            startActivity(intent)
        }

        img_cerrar = findViewById(R.id.img_cerrar)
        img_cerrar.setOnClickListener {
            var intent = Intent(this, catalogo_bodega::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}