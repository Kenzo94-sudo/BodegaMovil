package com.appbodega.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.appbodega.app.R
import com.appbodega.entity.Producto
import com.google.firebase.database.FirebaseDatabase

class DetalleProductoFragment : Fragment(R.layout.fragment_detalle_producto) {

    private lateinit var ivProducto: ImageView
    private lateinit var tvNombre: TextView
    private lateinit var tvCategoria: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvCantidad: TextView
    private lateinit var tvPrecioCompra: TextView
    private lateinit var tvPrecioVenta: TextView
    private lateinit var btnBack: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivProducto    = view.findViewById(R.id.ivProductoDetalle)
        tvNombre      = view.findViewById(R.id.tvNombreDetalle)
        tvCategoria   = view.findViewById(R.id.tvCategoriaDetalle)
        tvDescripcion = view.findViewById(R.id.tvDescripcionDetalle)
        tvCantidad    = view.findViewById(R.id.tvCantidadDetalle)
        tvPrecioCompra = view.findViewById(R.id.tvPrecioCompraDetalle)
        tvPrecioVenta  = view.findViewById(R.id.tvPrecioVentaDetalle)
        btnBack        = view.findViewById(R.id.btnBack)

        val productoId = arguments?.getString("productoId")
        if (productoId != null) cargarDetalle(productoId)

        btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun cargarDetalle(id: String) {
        FirebaseDatabase.getInstance().getReference("productos").child(id)
            .get().addOnSuccessListener { snapshot ->
                val producto = snapshot.getValue(Producto::class.java)
                producto?.let { mostrarProducto(it) }
            }
    }

    private fun mostrarProducto(producto: Producto) {
        tvNombre.text      = producto.nombre
        tvCategoria.text   = "Categoría: ${producto.categoria}"
        tvDescripcion.text = "Descripción: ${producto.descripcion}"
        tvCantidad.text    = "Stock: ${producto.cantidad}"
        tvPrecioCompra.text = "Precio Compra: S/. ${producto.precioCompra}"
        tvPrecioVenta.text  = "Precio Venta: S/. ${producto.precioVenta}"

        if (producto.imagenBase64.isNotEmpty()) {
            val bytes = Base64.decode(producto.imagenBase64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ivProducto.setImageBitmap(bitmap)
        }
    }
}