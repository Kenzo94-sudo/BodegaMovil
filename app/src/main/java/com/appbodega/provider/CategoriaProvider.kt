package com.appbodega.provider

import com.appbodega.app.R
import com.appbodega.entity.Categoria

object CategoriaProvider {

    val listaCategorias = listOf(

        Categoria(
            "Abarrotes",
            "Arroz, azúcar, fideos, aceite...",
            R.drawable.grocery_cataloge
        ),

        Categoria(
            "Bebidas",
            "Gaseosas, jugos y agua",
            R.drawable.drink_cataloge
        ),

        Categoria(
            "Snacks",
            "Papas, galletas y chocolates",
            R.drawable.snack_cataloge
        ),

        Categoria(
            "Limpieza",
            "Detergentes y desinfectantes",
            R.drawable.clean_cataloge
        ),

        Categoria(
            "Lácteos",
            "Leche, yogurt y queso",
            R.drawable.milk_cataloge
        ),

        Categoria(
            "Licores",
            "Cervezas, vinos y bebidas alcohólicas",
            R.drawable.beer_cataloge
        )

    )
}