package com.appbodega.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.ProductoAdapter
import com.appbodega.app.R
import com.appbodega.provider.AbarrotesProvider


class AbarrotesFragment : Fragment() {


    class AbarrotesFragment : Fragment(R.layout.fragment_abarrotes) {

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
            val lista = AbarrotesProvider.lista
            adapter.actualizar(lista)
        }
    }
}