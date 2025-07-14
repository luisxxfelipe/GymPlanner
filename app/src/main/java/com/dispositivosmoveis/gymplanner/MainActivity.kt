package com.dispositivosmoveis.gymplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dispositivosmoveis.gymplanner.ui.login.LoginActivity
import com.dispositivosmoveis.gymplanner.ui.treinos.TreinosActivity
import com.dispositivosmoveis.gymplanner.utils.SessaoManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usuarioId = SessaoManager.obterUsuarioId(this)

        if (usuarioId != -1L) {
            // Sessão ativa, vai direto para os treinos
            val intent = Intent(this, TreinosActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        } else {
            // Sem sessão, exibe tela de login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        finish() // Encerra MainActivity para não voltar nela
    }
}