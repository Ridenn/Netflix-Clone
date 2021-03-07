package com.example.netflixclone.View

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.netflixclone.R
import com.example.netflixclone.databinding.ActivityFormCadastroBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        toolbar()

        binding.btnCadastrar.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                showText("Preencha todos os campos para continuar")
            }else{
                registerUser()
            }
        }
    }

    private fun registerUser() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()

                    binding.editEmail.setText("")
                    binding.editPassword.setText("")
                    binding.msgErro.setText("")

                    Handler(Looper.getMainLooper()).postDelayed({
                        onBackPressed()
                    }, 100)
                }
            }.addOnFailureListener{

                var erro = it

                when{
                    erro is FirebaseAuthWeakPasswordException -> showText("A senha deve conter no mínimo 6 caracteres")
                    erro is FirebaseAuthUserCollisionException -> showText("Esta conta já foi cadastrada")
                    erro is FirebaseNetworkException -> showText("Sem conexão de rede")
                    else -> showText("Erro ao cadastrar usuário")
                }
            }
    }

    private fun toolbar(){
        val toolbar = binding.toolbarCadastro
        toolbar.setBackgroundColor(getColor(R.color.white))
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_netflix_official_logo))
    }

    private fun showText(text: String){
        binding.msgErro.setText(text)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.msgErro.setText("")
        }, 2000)
    }
}