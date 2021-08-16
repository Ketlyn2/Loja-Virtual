package com.loja.lojavirtual.Fragments

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.loja.lojavirtual.Model.Dados
import com.loja.lojavirtual.R
import kotlinx.android.synthetic.main.activity_cadastro_produtos.*
import java.util.*

class CadastroProdutos : AppCompatActivity() {

    private var btSelecionarFoto: Button? = null
    private var edtNome: EditText? = null
    private var edtPreco: EditText? = null
    private var btCadastrarProduto: AppCompatButton? = null
    private var fotoProduto: ImageView? = null

    private var selecionarUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produtos)

        getIds()

        btSelecionarFoto!!.setOnClickListener {
            selecionarFotoGaleria()
        }

        btCadastrarProduto!!.setOnClickListener {
            salvarDadosFirebase()
        }

    }

    fun getIds() {
        btSelecionarFoto = findViewById(R.id.bt_selecionar_foto)
        edtNome = findViewById(R.id.edit_nome)
        edtPreco = findViewById(R.id.edit_preco)
        btCadastrarProduto = findViewById(R.id.bt_cadastrar_produto)
        fotoProduto = findViewById(R.id.foto_produto)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            selecionarUri = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selecionarUri)
            fotoProduto!!.setImageBitmap(bitmap)
            btSelecionarFoto!!.alpha = 0f

        }

    }

    private fun selecionarFotoGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private fun salvarDadosFirebase() {

        val nomeArquivo = UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference("/imagens/${nomeArquivo}")

        selecionarUri?.let {
            referencia.putFile(it).addOnSuccessListener {
                referencia.downloadUrl.addOnSuccessListener {

                    val url = it.toString()
                    val nome = edtNome!!.text.toString()
                    val preco = edtPreco!!.text.toString()
                    val uid = FirebaseAuth.getInstance().uid

                    val Produtos = Dados(url, nome, preco)
                    FirebaseFirestore.getInstance().collection("Produtos")
                        .add(Produtos).addOnSuccessListener {
                            Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnFailureListener {

                        }

                }
            }
        }

    }

}