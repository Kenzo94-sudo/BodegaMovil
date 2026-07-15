package com.appbodega.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.ProductoAdapter
import com.appbodega.app.R
import com.appbodega.entity.Producto
import com.appbodega.repository.ProductoRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class BebidasFragment : Fragment(R.layout.fragment_bebidas) {

    private lateinit var recyclerProductos: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var btnRegistrarProducto: MaterialButton

    private lateinit var btnBack : ImageButton
    private lateinit var etBuscar: TextInputEditText
    private lateinit var productoRepository: ProductoRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productoRepository = ProductoRepository(requireContext())
        btnBack = view.findViewById(R.id.btnBack)
        recyclerProductos = view.findViewById(R.id.rvProductos)
        btnRegistrarProducto = view.findViewById(R.id.btnRegistrarProducto)
        etBuscar = view.findViewById(R.id.etBuscar)

        // 1. Layout Manager
        recyclerProductos.layoutManager =
            LinearLayoutManager(requireContext())

        // lista de guardado en BD
        adapter = ProductoAdapter(productoRepository.listarPorCategoria("Bebidas"))
        recyclerProductos.adapter = adapter

        etBuscar.addTextChangedListener { texto ->
            val filtrados = productoRepository.buscarPorNombre(texto.toString(), "Bebidas")
            adapter.actualizar(filtrados)
        }

        // 3. Listener de resultados (ANTES de navegar no importa, se mantiene)
        parentFragmentManager.setFragmentResultListener(
            "nuevo_producto",
            viewLifecycleOwner
        ) { _, bundle ->

            val producto = bundle.getSerializable("producto") as Producto

            // Guardar en SQLite
            productoRepository.insertar(producto)

            val filtroActual = etBuscar.text.toString()
            adapter.actualizar(
                productoRepository.buscarPorNombre(filtroActual, "Bebidas")
            )
        }

        // 4. Botón registrar
        btnRegistrarProducto.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flayContenedor, RegistrarProductoFragment())
                .addToBackStack(null)
                .commit()
        }

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}