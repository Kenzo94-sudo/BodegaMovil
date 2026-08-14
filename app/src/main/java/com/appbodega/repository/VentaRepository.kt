package com.appbodega.repository

import com.appbodega.entity.venta
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object VentaRepository {

    // Misma instancia de Firebase que ya usa AbarrotesFragment
    internal val ref = FirebaseDatabase.getInstance().getReference("ventas")

    /**
     * Registra una nueva venta en Firebase.
     * Genera el id y el codigo automáticamente.
     */
    fun registrar(
        venta: venta,
        onExito: (codigoGenerado: String) -> Unit,
        onError: (String) -> Unit
    ) {
        val nuevoId = ref.push().key ?: run {
            onError("No se pudo generar ID")
            return
        }
        // Código legible para mostrar en historial: "V-" + últimos 5 chars del push key
        val codigo = "V-${nuevoId.takeLast(5).uppercase()}"
        val ventaFinal = venta.copy(id = nuevoId, codigo = codigo)

        ref.child(nuevoId).setValue(ventaFinal)
            .addOnSuccessListener { onExito(codigo) }
            .addOnFailureListener { onError(it.message ?: "Error al registrar") }
    }

    /**
     * Escucha todas las ventas en tiempo real.
     * Se actualiza automáticamente cuando hay cambios en Firebase.
     */
    fun escucharTodas(
        onDatos: (List<venta>) -> Unit,
        onError: (String) -> Unit
    ): ValueEventListener {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = snapshot.children
                    .mapNotNull { it.getValue(venta::class.java) }
                    .sortedByDescending { it.fecha }
                onDatos(lista)
            }
            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }
        }
        ref.addValueEventListener(listener)
        return listener // devolvemos el listener para poder removerlo en onDestroy
    }

    /**
     * Obtiene ventas una sola vez (sin escucha continua) y aplica filtros locales.
     */
    fun obtenerFiltradas(
        metodo: String = "Todos",
        categoria: String = "Todas",
        onDatos: (List<venta>) -> Unit,
        onError: (String) -> Unit
    ) {
        ref.get()
            .addOnSuccessListener { snapshot ->
                var lista = snapshot.children
                    .mapNotNull { it.getValue(venta::class.java) }

                if (metodo != "Todos") lista = lista.filter { it.metodo == metodo }
                if (categoria != "Todas") lista = lista.filter { it.categoria == categoria }

                onDatos(lista.sortedByDescending { it.fecha })
            }
            .addOnFailureListener { onError(it.message ?: "Error al obtener ventas") }
    }

    /**
     * Suma el total de una lista de ventas ya filtrada.
     */
    fun calcularTotal(lista: List<venta>): Double = lista.sumOf { it.total }


    fun eliminar(
        id: String,
        onExito: () -> Unit,
        onError: (String) -> Unit
    ) {
        ref.child(id).removeValue()
            .addOnSuccessListener { onExito() }
            .addOnFailureListener { onError(it.message ?: "Error al eliminar") }
    }
}