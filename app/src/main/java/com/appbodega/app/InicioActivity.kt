package com.appbodega.app

import android.os.Bundle
import android.view.Menu
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView

class InicioActivity : AppCompatActivity() {

    private lateinit var dlaymenu: DrawerLayout
    private lateinit var nvMenu: NavigationView
    private lateinit var ivMenu : ImageView
    private lateinit var flayContenedor : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        dlaymenu = findViewById(R.id.dlaymenu)
        nvMenu = findViewById(R.id.nvMenu)
        ivMenu = findViewById(R.id.ivMenu)
        flayContenedor = findViewById(R.id.flayContenedor)

        ivMenu.setOnClickListener { dlaymenu.open() }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dlaymenu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}