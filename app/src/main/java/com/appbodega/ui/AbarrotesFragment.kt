package com.appbodega.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.Adapter.ProductoAdapter
import com.appbodega.app.R
import com.appbodega.entity.Producto
//import com.appbodega.repository.ProductoRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AbarrotesFragment : Fragment(R.layout.fragment_abarrotes) {

    private lateinit var recyclerProductos: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var btnBack: ImageButton
    private lateinit var etBuscar: TextInputEditText
//
//    private lateinit var productoRepository: ProductoRepository
    private val listaProductos = mutableListOf<Producto>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        productoRepository = ProductoRepository(requireContext())
        btnBack = view.findViewById(R.id.btnBack)
        recyclerProductos = view.findViewById(R.id.rvProductos)
        etBuscar = view.findViewById(R.id.etBuscar)
        adapter = ProductoAdapter(
            listaProductos,
            { producto ->
                val fragment = DetalleProductoFragment()
                val bundle = Bundle()
                bundle.putString("productoId", producto.id)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.flayContenedor, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            { producto ->
                val fragment = EditarProductoFragment()
                val bundle = Bundle()
                bundle.putSerializable("producto", producto)
                fragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.flayContenedor, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            { producto ->
                eliminarProducto(producto.id)
            }
        )
        recyclerProductos.layoutManager =
        LinearLayoutManager(requireContext())
        recyclerProductos.adapter = adapter
        cargarProductos()
        etBuscar.addTextChangedListener { texto ->
            val filtrados = listaProductos.filter {
                it.nombre.contains(
                            texto.toString(),
                            ignoreCase = true
                )
            }
            adapter.actualizar(filtrados)
        }
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    private fun cargarProductos() {
        FirebaseDatabase.getInstance()
            .getReference("productos")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {
                    listaProductos.clear()
                    for (item in snapshot.children) {
                        val producto =
                        item.getValue(
                                    Producto::class.java
                        )
                        if (
                            producto != null &&
                        producto.categoria == "Abarrotes"
                        ) {
                            listaProductos.add(producto)
                        }
                    }
                    adapter.actualizar(listaProductos)
                }
                override fun onCancelled(
                    error: DatabaseError
                ) {

                }
            })
    }

    private fun eliminarProducto(idProducto: String) {
        FirebaseDatabase.getInstance()
            .getReference("productos")
            .child(idProducto)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Producto eliminado",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun actualizarProducto(producto: Producto) {

        FirebaseDatabase.getInstance()
            .getReference("productos")
            .child(producto.id)
            .setValue(producto)
            .addOnSuccessListener {

                Toast.makeText(
                    requireContext(),
                    "Producto actualizado",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()
            }
    }
}
//
//        // 1. Layout Manager
//        recyclerProductos.layoutManager =
//            LinearLayoutManager(requireContext())
//
////        // 2. Adapter con lista inicial SIEMPRE segura
//////        adapter = ProductoAdapter(productoRepository.listarPorCategoria("Abarrotes"))
////        recyclerProductos.adapter = adapter
////
//////        etBuscar.addTextChangedListener { texto ->
//////            val filtrados = productoRepository.buscarPorNombre(texto.toString(), "Abarrotes")
//////            adapter.actualizar(filtrados)
////        }
//
//
//        // 3. Listener de resultados (ANTES de navegar no importa, se mantiene)
//        parentFragmentManager.setFragmentResultListener(
//            "nuevo_producto",
//            viewLifecycleOwner
//        ) { _, bundle ->
//
//            val producto = bundle.getSerializable("producto") as Producto
//
////            // sqlite guardar
////            productoRepository.insertar(producto)
//
//
////            val filtroActual = etBuscar.text.toString()
//////            adapter.actualizar(
//////                productoRepository.buscarPorNombre(filtroActual, "Abarrotes")
//////            )
////        }

//            btnBack.setOnClickListener {
//                parentFragmentManager.popBackStack()
//            }
//        }
//    }
//}