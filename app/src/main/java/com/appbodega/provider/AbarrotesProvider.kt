package com.appbodega.provider

import com.appbodega.entity.Producto

object AbarrotesProvider {

    val lista = listOf(

        Producto(
            nombre = "Arroz Costeño",
            descripcion = "Arroz extra 1kg",
            cantidad = 30,
            categoria = "Abarrotes",
            precioVenta = 4.50,
            precioCompra = 3.80,
            imagenBytes = null
        ),

        Producto(
            nombre = "Azúcar Rubia",
            descripcion = "Azúcar 1kg",
            cantidad = 25,
            categoria = "Abarrotes",
            precioVenta = 3.80,
            precioCompra = 3.10,
            imagenBytes = null
        ),

        Producto(
            nombre = "Fideos Nicolini",
            descripcion = "Pasta 500g",
            cantidad = 40,
            categoria = "Abarrotes",
            precioVenta = 2.20,
            precioCompra = 1.70,
            imagenBytes = null
        ),

        Producto(
            nombre = "Aceite Primor",
            descripcion = "Aceite vegetal 1L",
            cantidad = 18,
            categoria = "Abarrotes",
            precioVenta = 9.50,
            precioCompra = 7.80,
            imagenBytes = null
        ),

        Producto(
            nombre = "Sal Yodada",
            descripcion = "Sal de mesa 1kg",
            cantidad = 35,
            categoria = "Abarrotes",
            precioVenta = 1.50,
            precioCompra = 1.00,
            imagenBytes = null
        )
    )
}