package com.appbodega.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class inicio_sesion : AppCompatActivity() {

    private lateinit var btnAcceder: MaterialButton
    private lateinit var btnRegistrar: TextView
    private lateinit var txtEmail: TextInputEditText
    private lateinit var txtPass: TextInputEditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_sesion)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
            return
        }

        btnAcceder  = findViewById(R.id.btnAcceder)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        txtEmail    = findViewById(R.id.txtUsuario)
        txtPass     = findViewById(R.id.txtPassword)

        btnAcceder.setOnClickListener {
            loginUser()
        }

        btnRegistrar.setOnClickListener {
            Toast.makeText(
                this,
                "Comuníquese con el administrador para crear su cuenta",
                Toast.LENGTH_LONG
            ).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUser() {
        val email    = txtEmail.text.toString().trim()
        val password = txtPass.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingrese correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, InicioActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}