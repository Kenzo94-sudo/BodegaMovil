package com.appbodega.ui

import android.R.attr.fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.CategoriaAdapter
import com.appbodega.app.AbarroteFragment
import com.appbodega.app.AlcoholFragment
import com.appbodega.app.BebidaFragment
import com.appbodega.app.LimpiezaFragment
import com.appbodega.app.R
import com.appbodega.app.catalogo_abarrotes
import com.appbodega.app.catalogo_alcohol
import com.appbodega.app.catalogo_bebidas
import com.appbodega.app.catalogo_limpieza
import com.appbodega.app.catalogo_snacks
import com.appbodega.app.inicio_sesion
import com.appbodega.app.registro_ventas
import com.appbodega.provider.CategoriaProvider
import com.google.android.material.button.MaterialButton
import kotlin.jvm.java


class fragment_categoria : Fragment() {

    private lateinit var recyclerCategorias: RecyclerView
    private lateinit var adapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_categoria_bodega, container, false)
        recyclerCategorias = view.findViewById(R.id.rvCategorias)
        recyclerCategorias.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = CategoriaAdapter(CategoriaProvider.listaCategorias) { categoria ->

            val bundle = Bundle()
            bundle.putString("categoria", categoria.nombre)

            when (categoria.nombre) {
                "Abarrotes" -> AbarroteFragment()
                "Alcohol" -> AlcoholFragment()
                "Bebidas" -> BebidaFragment()
                "Limpieza" -> LimpiezaFragment()
                "Snacks" -> SnacksFragment()
                else -> AbarroteFragment()
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.flayContenedor, fragment)
                .addToBackStack(null)
                .commit()

        }
    }
}
