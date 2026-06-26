package com.appbodega.app

import android.graphics.Bitmap

data class Producto (
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val precio: Double,
    val imagenResId: Int
)