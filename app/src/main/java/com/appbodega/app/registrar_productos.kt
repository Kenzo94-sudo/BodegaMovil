package com.appbodega.app

// Permite acceder al permiso de cámara (Manifest.permission.CAMERA)
import android.Manifest
// Tipo de dato que representa una imagen en memoria (la foto que toma la cámara)
import android.graphics.Bitmap
// Clase que contiene el estado guardado de la pantalla (por si se rota el dispositivo)
import android.os.Bundle
// Elemento visual que muestra imágenes en el layout
import android.widget.ImageView
// Muestra mensajes emergentes cortos en pantalla
import android.widget.Toast
// Extiende el contenido de la app hasta los bordes de la pantalla
import androidx.activity.enableEdgeToEdge
// Contiene los contratos predefinidos para pedir permisos y abrir la cámara
import androidx.activity.result.contract.ActivityResultContracts
// Clase base de todas las pantallas (Activities) en Android
import androidx.appcompat.app.AppCompatActivity
// Permite verificar si un permiso ya fue concedido
import androidx.core.content.ContextCompat
// Constante que representa "permiso concedido" (valor numérico 0)
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
// Utilidad para escuchar los cambios en los márgenes del sistema (barra de estado, navegación)
import androidx.core.view.ViewCompat
// Representa los márgenes reservados por el sistema (barra de estado, barra de navegación)
import androidx.core.view.WindowInsetsCompat

class registrar_productos : AppCompatActivity() {

    // Variable que guardará la referencia al ImageView del layout
    private lateinit var imagen_producto: ImageView

    // Prepara la cámara y define qué hacer cuando el usuario tome una foto
    private val abrirCamara =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { foto: Bitmap? ->
            // Si el usuario tomó una foto (no canceló), la muestra en el ImageView
            foto?.let { imagen_producto.setImageBitmap(it) }
        }

    // Prepara el diálogo de permiso y define qué hacer según la respuesta del usuario
    private val pedirPermiso =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
            if (concedido) abrirCamara.launch(null) // Permiso aceptado → abre la cámara
            else Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Extiende el contenido hasta los bordes de la pantalla
        setContentView(R.layout.activity_registrar_productos) // Carga el layout de esta pantalla

        // Conecta la variable con el ImageView que está en el layout XML
        imagen_producto = findViewById(R.id.imagen_producto)

        // Cuando el usuario toque la imagen, verifica el permiso y abre la cámara
        imagen_producto.setOnClickListener { verificarPermisoCamara() }

        // Ajusta el padding para que el contenido no quede detrás de la barra de estado o navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun verificarPermisoCamara() {
        // Revisa si la app ya tiene permiso de cámara
        val permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        if (permiso == PERMISSION_GRANTED) abrirCamara.launch(null) // Ya tiene permiso → abre la cámara
        else pedirPermiso.launch(Manifest.permission.CAMERA)        // No tiene permiso → lo solicita
    }
}