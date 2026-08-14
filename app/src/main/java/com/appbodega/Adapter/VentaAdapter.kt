package com.appbodega.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appbodega.app.R
import com.appbodega.entity.venta

class VentaAdapter(
    private var lista: List<venta>,
    private val onVerDetalle: (venta) -> Unit
) : RecyclerView.Adapter<VentaAdapter.VentaViewHolder>() {

    inner class VentaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCodigo:   TextView    = view.findViewById(R.id.txtCodigo)
        val txtFecha:    TextView    = view.findViewById(R.id.txtFecha)
        val txtCantidad: TextView    = view.findViewById(R.id.txtCantidad)
        val txtMetodo:   TextView    = view.findViewById(R.id.txtMetodo)
        val txtTotal:    TextView    = view.findViewById(R.id.txtTotal)
        val btnVer:      ImageButton = view.findViewById(R.id.btnVer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_venta, parent, false)
        return VentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        val v = lista[position]
        holder.txtCodigo.text   = v.codigo
        holder.txtFecha.text    = v.fecha
        holder.txtCantidad.text = v.cantidad.toString()
        holder.txtMetodo.text   = v.metodo
        holder.txtTotal.text    = "S/ %.2f".format(v.total)
        holder.btnVer.setOnClickListener { onVerDetalle(v) }
    }

    override fun getItemCount() = lista.size

    fun actualizar(nuevaLista: List<venta>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}