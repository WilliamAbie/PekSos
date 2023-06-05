package com.wa.peksos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.wa.peksos.databinding.ActivityLoginAdminBinding

class LoginAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login_admin)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnLoginAdmin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()

            if (email.isNotEmpty()){
                if (pass.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this, MenuUtama::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else{
                    binding.etPassword.error = "Masukkan Password"
                }
            }
            else{
                binding.etEmail.error = "Masukkan E-mail"
            }
        }

        binding.textView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}