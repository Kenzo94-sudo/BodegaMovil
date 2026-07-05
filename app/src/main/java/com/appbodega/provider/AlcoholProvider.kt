package com.appbodega.provider

import com.appbodega.entity.Producto

object AlcoholProvider {

    val lista = listOf(

        Producto(
            nombre = "Cerveza Pilsen",
            descripcion = "Lata 355ml",
            cantidad = 40,
            categoria = "Alcohol",
            precioVenta = 5.00,
            precioCompra = 3.90,
            imagenBytes = null
        ),

        Producto(
            nombre = "Cerveza Cusqueña",
            descripcion = "Botella 620ml",
            cantidad = 25,
            categoria = "Alcohol",
            precioVenta = 7.50,
            precioCompra = 6.00,
            imagenBytes = null
        ),

        Producto(
            nombre = "Vino Tinto Santiago Queirolo",
            descripcion = "Botella 750ml",
            cantidad = 18,
            categoria = "Alcohol",
            precioVenta = 18.00,
            precioCompra = 14.50,
            imagenBytes = null
        ),

        Producto(
            nombre = "Ron Cartavio",
            descripcion = "Botella 750ml",
            cantidad = 12,
            categoria = "Alcohol",
            precioVenta = 25.00,
            precioCompra = 20.00,
            imagenBytes = null
        ),

        Producto(
            nombre = "Whisky Johnnie Walker Red",
            descripcion = "Botella 750ml",
            cantidad = 8,
            categoria = "Alcohol",
            precioVenta = 95.00,
            precioCompra = 80.00,
            imagenBytes = null
        )
    )
}