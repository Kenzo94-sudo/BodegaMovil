package com.appbodega.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.ProductoAdapter
import com.appbodega.app.R
import com.appbodega.entity.Producto
import com.appbodega.provider.AbarrotesProvider
import com.google.android.material.button.MaterialButton

class AbarrotesFragment : Fragment(R.layout.fragment_abarrotes) {

    private lateinit var recyclerProductos: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var btnRegistrarProducto: MaterialButton
    private lateinit var btnBack : ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack = view.findViewById(R.id.btnBack)
        recyclerProductos = view.findViewById(R.id.rvProductos)
        btnRegistrarProducto = view.findViewById(R.id.btnRegistrarProducto)

        // 1. Layout Manager
        recyclerProductos.layoutManager =
            LinearLayoutManager(requireContext())

        // 2. Adapter con lista inicial SIEMPRE segura
        adapter = ProductoAdapter(AbarrotesProvider.lista.toList())
        recyclerProductos.adapter = adapter

        // 3. Listener de resultados (ANTES de navegar no importa, se mantiene)
        parentFragmentManager.setFragmentResultListener(
            "nuevo_producto",
            viewLifecycleOwner
        ) { _, bundle ->

            val producto = bundle.getSerializable("producto") as Producto

            // Guardar en provider
            AbarrotesProvider.lista.add(producto)

            // Refrescar UI
            adapter.actualizar(AbarrotesProvider.lista)
        }

        // 4. Botón registrar
        btnRegistrarProducto.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flayContenedor, RegistrarProductoFragment())
                .addToBackStack(null)
                .commit()
        }

        // 5. Cargar inicial (solo una vez)
        adapter.actualizar(AbarrotesProvider.lista)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}