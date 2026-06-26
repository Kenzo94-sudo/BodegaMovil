package com.appbodega.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(
    private val productos: List<Producto>
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.img_producto)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionProducto)
        val tvCantidad: TextView = view.findViewById(R.id.tvCantidadProducto)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioProducto)
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.imgProducto.setImageResource(producto.imagenResId)
        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion
        holder.tvCantidad.text = "Cantidad: ${producto.cantidad}"
        holder.tvPrecio.text = "S/. ${producto.precioVenta}"
    }

    override fun getItemCount(): Int = productos.size
}