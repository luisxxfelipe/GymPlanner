package com.dispositivosmoveis.gymplanner.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.ui.treinos.TreinosActivity

class LoginActivity: AppCompatActivity() {
    private lateinit var etUsuario: EditText
    private lateinit var etSenha: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsuario = findViewById(R.id.etUsuario)
        etSenha = findViewById(R.id.etSenha)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            realizarLogin()
        }
    }

    private fun realizarLogin() {
        val usuario = etUsuario.text.toString()
        val senha = etSenha.text.toString()

        // validações na hora do login

        if (usuario.isEmpty() || senha.isEmpty()) {
            etUsuario.error = "Preencha o usuário"
            etSenha.error = "Preencha a senha"
            return
        }

        if(usuario == "admin" && senha == "123456"){

            val intent = Intent(this, TreinosActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            etUsuario.error = "Usuário ou senha incorretos"
            etSenha.error = "Usuário ou senha incorretos"
        }
    }
}