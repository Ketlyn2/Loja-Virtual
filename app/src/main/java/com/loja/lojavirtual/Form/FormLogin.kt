package com.loja.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.loja.lojavirtual.R
import com.loja.lojavirtual.TelaPrincipal

class FormLogin : AppCompatActivity() {

    var edtEmail: TextInputLayout? = null
    var edtSenha: TextInputLayout? = null
    var btLogin: AppCompatButton? = null
    var btTextCadastro: TextView? = null
    var frameLayout: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        supportActionBar!!.hide()

        getIds()

        verificarUsuarioLogado()

        btTextCadastro!!.setOnClickListener {
            var intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        btLogin!!.setOnClickListener {
            autenticarUsuario()
        }

    }

    fun getIds() {
        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        btLogin = findViewById(R.id.btnLogin)
        btTextCadastro = findViewById(R.id.btcadastrar)
        frameLayout = findViewById(R.id.frameLayout)
    }

    private fun autenticarUsuario() {

        val email = edtEmail!!.editText!!.text.toString()
        val senha = edtSenha!!.editText!!.text.toString()
        var relativeLayout = findViewById<RelativeLayout>(R.id.relativeLogin)

        if (email.isEmpty()) {
            //Toast.makeText(this, "Digite seu email!", Toast.LENGTH_SHORT).show()
            var snackbar =
                Snackbar.make(relativeLayout, "Digite seu email!", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                    .setAction("Ok", View.OnClickListener {

                    })
            snackbar.show()

        } else if (senha.isEmpty()) {
            //Toast.makeText(this, "Coloque sua senha!", Toast.LENGTH_SHORT).show()
            var snackbar =
                Snackbar.make(relativeLayout, "Digite sua senha !", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                    .setAction("Ok", View.OnClickListener {

                    })
            snackbar.show()
        } else if (email.isEmpty() && senha.isEmpty()) {
            //Toast.makeText(this, "Coloque o seu email e sua senha!", Toast.LENGTH_SHORT).show()
            var snackbar =
                Snackbar.make(relativeLayout, "Digite seu email e senha!", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                    .setAction("Ok", View.OnClickListener {

                    })
            snackbar.show()
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener {
                if(it.isSuccessful) {
                    frameLayout!!.visibility = View.VISIBLE
                    Handler().postDelayed({abrirTelaPrincipal()}, 3000)

                }
            }.addOnFailureListener {
                var snackbar =
                    Snackbar.make(relativeLayout, "Erro ao logar usuário", Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                        .setAction("Ok", View.OnClickListener {

                        })
                snackbar.show()
            }
        }
    }

    private fun abrirTelaPrincipal() {
        var intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
    }

    private fun verificarUsuarioLogado() {

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if(usuarioAtual != null) {
            abrirTelaPrincipal()
        }

    }

}