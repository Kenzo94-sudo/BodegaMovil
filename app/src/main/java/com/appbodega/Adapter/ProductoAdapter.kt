package com.appbodega.Adapter
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.app.R
import com.appbodega.entity.Producto

private  val STOCK_MINIMO = 5
class ProductoAdapter(

            private var productos: List<Producto>,
            private val onActualizar: (Producto) -> Unit,
            private val onEliminar: (Producto) -> Unit
        ) : RecyclerView.Adapter<ProductoViewHolder>() { //El recycler view avisa que trabajara junto al adaptador
        // Mapea los componentes visuales de cada fila (item_producto)

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
        holder.tvPrecio.text = "S/. ${producto.precioVenta}" // Muestra el precio de venta

        // Alerta de stock bajo:si el stock es bajo se pondra rojo
        if (producto.cantidad < STOCK_MINIMO) {
            holder.tvCantidad.text = "Cantidad: ${producto.cantidad} ⚠ Stock bajo"
            holder.tvCantidad.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.red)
            )
        } else {
            holder.tvCantidad.text = "Cantidad: ${producto.cantidad}"
            holder.tvCantidad.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.texto_secundario)
            )
        }

        holder.btnEliminar.setOnClickListener {
            // Confirmación antes de eliminar el producto
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar producto")
                .setMessage("¿Estas seguro que quieres eliminar \"${producto.nombre}\"? Esta accion no se puede deshacer.")
                .setPositiveButton("Eliminar") { dialog, _ ->
                    onEliminar(producto)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        holder.btnActualizar.setOnClickListener {
            onActualizar(producto)
        }

        // Convierte los bytes guardados de vuelta a una imagen visible
        if (producto.imagenBase64.isNotEmpty()) {
            try {
                val bytes = Base64.decode(producto.imagenBase64,Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(
                    bytes, 0, bytes.size)
                holder.imgProducto.setImageBitmap(bitmap)
            } catch (e: Exception) {
                holder.imgProducto.setImageResource(
                    R.drawable.icono_camara)}
        } else { holder.imgProducto.setImageResource(
                R.drawable.icono_camara)}}

    // 4. Dice cuántos productos hay en total
    override fun getItemCount(): Int = productos.size

    fun actualizar(data: List<Producto>) {
        productos = data
        notifyDataSetChanged()
    }
}
