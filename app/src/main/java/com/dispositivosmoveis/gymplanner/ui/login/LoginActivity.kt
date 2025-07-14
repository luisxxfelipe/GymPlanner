package com.dispositivosmoveis.gymplanner.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dispositivosmoveis.gymplanner.R
import com.dispositivosmoveis.gymplanner.database.AppDatabase
import com.dispositivosmoveis.gymplanner.ui.registro.RegistroActivity
import com.dispositivosmoveis.gymplanner.ui.treinos.TreinosActivity
import com.dispositivosmoveis.gymplanner.utils.SessaoManager
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {
    private lateinit var etUsuario: EditText
    private lateinit var etSenha: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsuario = findViewById(R.id.etUsuario)
        etSenha = findViewById(R.id.etSenha)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        btnLogin.setOnClickListener {
            realizarLogin()
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun realizarLogin() {
        val usuarioTexto = etUsuario.text.toString()
        val senhaTexto = etSenha.text.toString()

        if (usuarioTexto.isBlank() || senhaTexto.isBlank()) {
            etUsuario.error = "Preencha o usuário"
            etSenha.error = "Preencha a senha"
            return
        }

        val usuarioDao = AppDatabase.getDatabase(this).userDao()

        lifecycleScope.launch {
            val usuarioLogado = usuarioDao.login(usuarioTexto, senhaTexto)

            if (usuarioLogado != null) {
                val intent = Intent(this@LoginActivity, TreinosActivity::class.java)
                intent.putExtra("usuarioId", usuarioLogado.id)
                SessaoManager.salvarUsuarioId(this@LoginActivity, usuarioLogado.id)
                startActivity(intent)
                finish()
            } else {
                runOnUiThread {
                    etUsuario.error = "Usuário ou senha inválidos"
                    etSenha.error = "Usuário ou senha inválidos"
                }
            }
        }
    }

}