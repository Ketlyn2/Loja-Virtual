package com.loja.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.loja.lojavirtual.R

class FormCadastro : AppCompatActivity() {

    var edtNewEmail: TextInputLayout? = null
    var edtNewSenha: TextInputLayout? = null
    var btCadastrar: AppCompatButton? = null
    var btVoltarLogin: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        supportActionBar!!.hide()

        getIds()

        btCadastrar!!.setOnClickListener {
            cadastrarUsuario()
        }

        btVoltarLogin!!.setOnClickListener {
            voltarTelaLogin()
        }

    }

    fun getIds() {
        edtNewEmail = findViewById(R.id.edtEmail)
        edtNewSenha = findViewById(R.id.edtSenha)
        btCadastrar = findViewById(R.id.btnCadastrar)
        btVoltarLogin = findViewById(R.id.btVoltar)
    }

    private fun cadastrarUsuario() {
        var email = edtNewEmail!!.editText!!.text.toString()
        var senha = edtNewSenha!!.editText!!.text.toString()
        var relativeLayout = findViewById<RelativeLayout>(R.id.relativeCadastro)

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
                Snackbar.make(relativeLayout, "Digite sua senha!", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                    .setAction("Ok", View.OnClickListener {

                    })
            snackbar.show()
        } else if (email.isEmpty() && senha.isEmpty()) {
            //Toast.makeText(this, "Coloque o seu email e sua senha!", Toast.LENGTH_SHORT).show()
            var snackbar =
                Snackbar.make(relativeLayout, "Digite seu email e sua senha!", Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                    .setAction("Ok", View.OnClickListener {

                    })
            snackbar.show()
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        //Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT) .show()
                        var snackbar =
                            Snackbar.make(relativeLayout, "Cadastro realizado com sucesso!", Snackbar.LENGTH_INDEFINITE)
                                .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                                .setAction("Ok", View.OnClickListener {
                                    voltarTelaLogin()
                                })
                        snackbar.show()
                    }

                }.addOnFailureListener {
                //Toast.makeText(this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
                    var snackbar =
                        Snackbar.make(relativeLayout, "Erro ao cadastrar usuário", Snackbar.LENGTH_INDEFINITE)
                            .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK)
                            .setAction("Ok", View.OnClickListener {

                            })
                    snackbar.show()
            }
        }

    }

    fun voltarTelaLogin() {
        var intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
    }
}