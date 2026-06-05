package com.appbodega.mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appbodega.app.R
import com.appbodega.app.catalogo_bodega
import com.google.android.material.button.MaterialButton

class catalogo_snacks : AppCompatActivity() {
    private lateinit var  btnAtras: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_catalogo_snacks)
        btnAtras=findViewById(R.id.btnAtras)
        btnAtras.setOnClickListener {
            var intent= Intent(this, catalogo_bodega::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}