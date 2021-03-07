package com.example.netflixclone.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.netflixclone.databinding.ActivityFormLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException


class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        verifyLoggedUser()
        setupButtons()
        handleErrorMessage()
    }

    private fun handleErrorMessage() {
        binding.editEmail.doAfterTextChanged {
            showText("")
        }

        binding.editPassword.doAfterTextChanged {
            showText("")
        }
    }

    private fun setupButtons() {
        binding.txtCadastroNovo.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btnEntrar.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                showText("Preencha todos os campos!")
            }else{
                authenticateUser()
            }
        }
    }

    private fun authenticateUser() {

        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        showProgress(true)
        hideKeyboard(binding.editEmail)

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                moviesList()
            }
        }.addOnFailureListener {
            showProgress(false)

            val erro = it

            when{
                erro is FirebaseAuthInvalidCredentialsException -> showText("Email ou senha incorretos")
                erro is FirebaseNetworkException -> showText("Sem conexão de rede")
                else -> showText("Erro ao logar usuário")
            }
        }
    }

    private fun verifyLoggedUser(){
        val loggedUser = FirebaseAuth.getInstance().currentUser
        if(loggedUser != null){
            moviesList()
        }
    }

    private fun moviesList() {
        val intent = Intent(this, ListaFilmes::class.java)
        startActivity(intent)
        finish()
    }

    private fun showText(text: String){
        binding.mensagemErro.setText(text)

//        Handler(Looper.getMainLooper()).postDelayed({
//            binding.mensagemErro.setText("")
//        }, 2000)
    }

    private fun showProgress(show: Boolean) {
        binding.progressBar.isVisible = show
        binding.conteudoTelaLogin.isVisible = !show
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}