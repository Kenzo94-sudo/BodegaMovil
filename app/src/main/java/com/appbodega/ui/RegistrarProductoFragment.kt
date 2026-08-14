package com.appbodega.ui

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.appbodega.app.R
import com.appbodega.entity.Producto
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import android.content.pm.PackageManager
import com.google.firebase.database.FirebaseDatabase
import android.util.Base64
import java.util.UUID


class RegistrarProductoFragment :
    Fragment(R.layout.fragment_registrar_productos) {

    private lateinit var imagen: ImageView
    private lateinit var etNombre: TextInputEditText
    private lateinit var etCantidad: TextInputEditText
    private lateinit var etPrecioCompra: TextInputEditText
    private lateinit var etPrecioVenta: TextInputEditText
    private lateinit var etDescripcion: TextInputEditText
    private lateinit var spinnerCategorias: Spinner
    private lateinit var btnRegistrar: MaterialButton
    private lateinit var btnCancelar: MaterialButton

    private var fotoProducto: Bitmap? = null

    private val camara =
        registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { foto ->

            if (foto != null) {
                fotoProducto = foto
                imagen.setImageBitmap(foto)
            }
        }

    private val permiso =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                camara.launch(null)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permiso denegado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        imagen = view.findViewById(R.id.imagen_producto)
        etNombre = view.findViewById(R.id.nombre_nuevo_producto)
        etCantidad = view.findViewById(R.id.cantidad_nuevo_producto)
        etPrecioCompra = view.findViewById(R.id.precio_compra_nuevo_producto)
        etPrecioVenta = view.findViewById(R.id.precio_venta_nuevo_producto)
        etDescripcion = view.findViewById(R.id.descripcion_nuevo_producto)
        spinnerCategorias = view.findViewById(R.id.spinner_categorias)

        btnRegistrar = view.findViewById(R.id.btnAcceder)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        imagen.setOnClickListener {

            val permisoCamara =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                )

            if (permisoCamara ==
                PackageManager.PERMISSION_GRANTED
            ) {

                camara.launch(null)

            } else {

                permiso.launch(Manifest.permission.CAMERA)
            }
        }

        btnRegistrar.setOnClickListener {
            guardarDatos()
        }

        btnCancelar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun guardarDatos() {

        val txtNombre =
            etNombre.text.toString().trim()

        val txtCantidad =
            etCantidad.text.toString().trim()

        val txtCompra =
            etPrecioCompra.text.toString().trim()

        val txtVenta =
            etPrecioVenta.text.toString().trim()

        val txtDesc =
            etDescripcion.text.toString().trim()

        val txtCat =
            spinnerCategorias.selectedItem?.toString() ?: ""

        if (
            txtNombre.isEmpty() ||
            txtCantidad.isEmpty() ||
            txtCompra.isEmpty() ||
            txtVenta.isEmpty()
        ) {

            Toast.makeText(
                requireContext(),
                "Completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()

            return
        }
        val cantidad = txtCantidad.toIntOrNull()
        val compra = txtCompra.toDoubleOrNull()
        val venta = txtVenta.toDoubleOrNull()

        if (cantidad == null || compra == null || venta == null
        ) {
            Toast.makeText(
                requireContext(),
                "Ingrese valores numéricos válidos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        var fotoBytes: ByteArray? = null

        fotoProducto?.let {
            val stream = ByteArrayOutputStream()
            it.compress(
                Bitmap.CompressFormat.JPEG,
                70, stream
            )
            fotoBytes = stream.toByteArray()
        }

        var imagenBase64 = ""
        fotoBytes?.let {
            imagenBase64 = Base64.encodeToString(
                it,
                Base64.DEFAULT
            )
        }

        val idProducto = UUID.randomUUID().toString()

        val nuevoProducto = Producto(
                    id = idProducto,
                    nombre = txtNombre,
                    descripcion = txtDesc,
                    cantidad = cantidad,
                    categoria = txtCat,
                    precioCompra = compra,
                    precioVenta = venta,
                    imagenBase64 = imagenBase64
        )
        val db = FirebaseDatabase.getInstance().reference

        db.child("productos")
            .child(idProducto)
            .setValue(nuevoProducto)
            .addOnSuccessListener {

                Toast.makeText(
                    requireContext(),
                    "Producto registrado correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                limpiarCampos()
            }
            .addOnFailureListener { e ->

                Toast.makeText(
                    requireContext(),
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun limpiarCampos() {

        etNombre.setText("")
        etCantidad.setText("")
        etPrecioCompra.setText("")
        etPrecioVenta.setText("")
        etDescripcion.setText("")

        spinnerCategorias.setSelection(0)

        fotoProducto = null

        imagen.setImageDrawable(null)
    }
}