package com.appbodega.app
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class ProductoAdapter(
    private val productos: List<Producto> // Recibe la lista de productos
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() { //El recycler view avisa que trabajara junto al adaptador
    // Mapea los componentes visuales de cada fila (item_producto)
    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.img_producto)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionProducto)
        val tvCantidad: TextView = view.findViewById(R.id.tvCantidadProducto)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioProducto)
    }
    // 2. Infla el diseño XML de la fila
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)//busca la ubicaciones de los productos por item, y evita las busquedas repetidas
    }

    // 3. Pone los datos del producto en los textos e imagen de la fila
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position] // Obtiene el producto actual

        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion
        holder.tvCantidad.text = "Cantidad: ${producto.cantidad}"
        holder.tvPrecio.text = "S/. ${producto.precioVenta}" // Muestra el precio de venta

        // Convierte los bytes guardados de vuelta a una imagen visible
        val bytes = producto.imagenBytes
        if (bytes != null && bytes.isNotEmpty()) {
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            holder.imgProducto.setImageBitmap(bitmap)
        } else {
            holder.imgProducto.setImageResource(R.drawable.icono_camara) // Foto por defecto
        }
    }

    // 4. Dice cuántos productos hay en total
    override fun getItemCount(): Int = productos.size
}