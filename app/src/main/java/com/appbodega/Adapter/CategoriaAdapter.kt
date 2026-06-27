package com.appbodega.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.app.R
import com.appbodega.entity.Categoria

class CategoriaAdapter (

    private val lista: List<Categoria>,
    private val onClick: (Categoria) -> Unit )
    : RecyclerView.Adapter<CategoriaAdapter.ViewHolder>()
    {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val img = view.findViewById<ImageView>(R.id.imgCategoria)
            val nombre = view.findViewById<TextView>(R.id.tvNombreCategoria)
            val desc = view.findViewById<TextView>(R.id.tvDescripcion)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_categoria, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = lista[position]

            holder.nombre.text = item.nombre
            holder.desc.text = item.descripcion
            holder.img.setImageResource(item.imagen)

            holder.itemView.setOnClickListener {
                onClick(item)
            }
        }

        override fun getItemCount() = lista.size
    }
