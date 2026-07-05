package com.appbodega.ui

import android.R.attr.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.CategoriaAdapter
import com.appbodega.ui.BebidasFragment
import com.appbodega.ui.LimpiezaFragment
import com.appbodega.ui.SnacksFragment
import com.appbodega.ui.AbarrotesFragment
import com.appbodega.ui.AlcoholFragment
import com.appbodega.ui.CategoriasFragment
import com.appbodega.app.InicioActivity
import com.appbodega.app.R
import com.appbodega.provider.CategoriaProvider



class CategoriasFragment : Fragment() {

    private lateinit var btnMenu: ImageView

    private lateinit var btnCerrar: ImageView
    private lateinit var recyclerCategorias: RecyclerView
    private lateinit var adapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_categoria_bodega, container, false)

        btnMenu = view.findViewById(R.id.btnMenu)
        btnCerrar = view.findViewById(R.id.btnCerrar)

        btnMenu.setOnClickListener {
            (activity as InicioActivity).abrirMenu()
        }

        btnCerrar.setOnClickListener {
            requireActivity()
                .finish()
        }

        recyclerCategorias = view.findViewById(R.id.rvCategorias)
        recyclerCategorias.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = CategoriaAdapter(CategoriaProvider.listaCategorias) { categoria ->

            val fragment = when (categoria.nombre) {
                "Abarrotes" -> AbarrotesFragment()
                "Alcohol" -> AlcoholFragment()
                "Bebidas" -> BebidasFragment()
                "Limpieza" -> LimpiezaFragment()
                "Snacks" -> SnacksFragment()
                else -> CategoriasFragment()
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.flayContenedor, fragment as Fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerCategorias.adapter = adapter

        return view
    }
}
