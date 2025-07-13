package com.dispositivosmoveis.gymplanner.ui.register

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.database.AppDatabase
import com.dispositivosmoveis.gymplanner.entities.User
import com.dispositivosmoveis.gymplanner.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNome: EditText
    private lateinit var etUsuario: EditText
    private lateinit var etSenha: EditText
    private lateinit var etConfirmarSenha: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var ivBackArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etNome = findViewById(R.id.etNome)
        etUsuario = findViewById(R.id.etUsuario)
        etSenha = findViewById(R.id.etSenha)
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        ivBackArrow = findViewById(R.id.ivBackArrow)

        ivBackArrow.setOnClickListener {
            finish()
        }

        btnRegistrar.setOnClickListener {
            realizarRegistro()
        }
    }

    private fun realizarRegistro() {
        val nome = etNome.text.toString()
        val usuario = etUsuario.text.toString()
        val senha = etSenha.text.toString()
        val confirmarSenha = etConfirmarSenha.text.toString()

        if (nome.isBlank() || usuario.isBlank() || senha.isBlank() || confirmarSenha.isBlank()) {
            Toast.makeText(
                this,
                "Por favor, preencha todos os campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (senha != confirmarSenha) {
            Toast.makeText(
                this,
                "As senhas não coincidem",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (senha.length < 6) {
            Toast.makeText(
                this,
                "A senha deve ter pelo menos 6 caracteres",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val authRepository = AuthRepository(AppDatabase.getDatabase(this).userDao())

        lifecycleScope.launch {
            try {
                authRepository.registerUser(User(username = usuario, password = senha))
                Toast.makeText(
                    this@RegisterActivity,
                    "Conta criada com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Erro ao criar conta: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
