package com.appbodega.entity

import java.io.Serializable

data class Producto(
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val categoria: String,
    val precioVenta: Double,
    val precioCompra: Double,
    val imagenBytes: ByteArray?, // se usa como un formato comprimido
                                // y no sobre pase el excesivo peso de megas que podria ocupar
    val id: Int = 0
) : Serializable // esto me permite convertir un objeto en datos para poder enviarlo entre pantallas o almacenarlo.