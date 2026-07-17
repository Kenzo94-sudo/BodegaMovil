package com.appbodega.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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

class RegistrarProductoFragment : Fragment(R.layout.fragment_registrar_productos) {

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
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { foto ->
            if (foto != null) {
                fotoProducto = foto
                imagen.setImageBitmap(foto)
            }
        }

    private val permiso =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                camara.launch(null)
            } else {
                Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            val permisoCamara = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            )

            if (permisoCamara == PackageManager.PERMISSION_GRANTED) {
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

        val txtNombre = etNombre.text.toString()
        val txtCantidad = etCantidad.text.toString()
        val txtCompra = etPrecioCompra.text.toString()
        val txtVenta = etPrecioVenta.text.toString()
        val txtDesc = etDescripcion.text.toString()
        val txtCat = spinnerCategorias.selectedItem?.toString() ?: ""

        if (txtNombre.isEmpty() || txtCantidad.isEmpty() ||
            txtCompra.isEmpty() || txtVenta.isEmpty()
        ) {
            Toast.makeText(requireContext(), "Completa los campos", Toast.LENGTH_SHORT).show()
            return
        }

        var fotoBytes: ByteArray? = null

        fotoProducto?.let {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            fotoBytes = stream.toByteArray()
        }

        val nuevoProducto = Producto(
            nombre = txtNombre,
            descripcion = txtDesc,
            cantidad = txtCantidad.toInt(),
            categoria = txtCat,
            precioCompra = txtCompra.toDouble(),
            precioVenta = txtVenta.toDouble(),
            imagenBytes = fotoBytes
        )


        parentFragmentManager.setFragmentResult(
            "nuevo_producto",
            Bundle().apply {
                putSerializable("producto", nuevoProducto)
            }
        )

        parentFragmentManager.popBackStack()
    }
}
