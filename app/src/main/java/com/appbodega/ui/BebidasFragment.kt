package com.appbodega.app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.ProductoAdapter
import com.appbodega.provider.BebidasProvider

class BebidasFragment : Fragment(R.layout.fragment_abarrotes) {

    private lateinit var recyclerProductos: RecyclerView
    private lateinit var adapter: ProductoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerProductos = view.findViewById(R.id.rvProductos)

        recyclerProductos.layoutManager =
            LinearLayoutManager(requireContext())

        adapter = ProductoAdapter(emptyList())
        recyclerProductos.adapter = adapter

        cargarProductos()
    }

    private fun cargarProductos() {
        val lista = BebidasProvider.lista
        adapter.actualizar(lista)
    }
}