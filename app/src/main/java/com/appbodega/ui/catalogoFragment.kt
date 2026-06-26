package com.appbodega.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.appbodega.app.R
import com.appbodega.app.catalogo_abarrotes
import com.appbodega.app.catalogo_alcohol
import com.appbodega.app.catalogo_bebidas
import com.appbodega.app.catalogo_limpieza
import com.appbodega.app.catalogo_snacks
import com.appbodega.app.inicio_sesion
import com.appbodega.app.registro_ventas
import com.google.android.material.button.MaterialButton
import kotlin.jvm.java


class catalogoFragment : Fragment() {


    private lateinit var ivSnacks: ImageView
    private lateinit var ivAbarrotes: ImageView
    private lateinit var ivAlcohol: ImageView
    private lateinit var ivLimpieza: ImageView
    private lateinit var ivBebidas: ImageView

    private lateinit var btnRegistroVentas: MaterialButton
    private lateinit var btnSalir: MaterialButton

    private lateinit var imgCerrar: ImageView
    private lateinit var imgMenu: ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivSnacks = view.findViewById(R.id.iv_snacks)
        ivSnacks.setOnClickListener {
            val intent = Intent(requireContext(), catalogo_snacks::class.java)
            startActivity(intent)
        }

        ivBebidas = view.findViewById(R.id.iv_bebidas)
        ivBebidas.setOnClickListener {
            val intent = Intent(requireContext(), catalogo_bebidas::class.java)
            startActivity(intent)
        }

        ivAbarrotes = view.findViewById(R.id.iv_abarrotes)
        ivAbarrotes.setOnClickListener {
            val intent = Intent(requireContext(), catalogo_abarrotes::class.java)
            startActivity(intent)
        }

        ivLimpieza = view.findViewById(R.id.iv_limpieza)
        ivLimpieza.setOnClickListener {
            val intent = Intent(requireContext(), catalogo_limpieza::class.java)
            startActivity(intent)
        }

        ivAlcohol = view.findViewById(R.id.iv_alcohol)
        ivAlcohol.setOnClickListener {
            val intent = Intent(requireContext(), catalogo_alcohol::class.java)
            startActivity(intent)
        }

        btnRegistroVentas = view.findViewById(R.id.btnRegistroVentas)
        btnRegistroVentas.setOnClickListener {
            val intent = Intent(requireContext(), registro_ventas::class.java)
            startActivity(intent)
        }

        btnSalir = view.findViewById(R.id.btnSalir)
        btnSalir.setOnClickListener {
            val intent = Intent(requireContext(), inicio_sesion::class.java)
            startActivity(intent)
        }

        imgCerrar = view.findViewById(R.id.img_cerrar)
        imgCerrar.setOnClickListener {
            val intent = Intent(requireContext(), inicio_sesion::class.java)
            startActivity(intent)
        }

        imgMenu = view.findViewById(R.id.img_menu)
        imgMenu.setOnClickListener {
            val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.dlaymenu)
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}