package com.wa.peksos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.wa.peksos.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            val confirmPass = binding.etConfirmPassword.text.toString()
            if (email.isNotEmpty()){
                if (pass.isNotEmpty()){
                    if (confirmPass.isNotEmpty()){
                        if (confirmPass == pass){
                            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                                if (it.isSuccessful){
                                    Toast.makeText(this, "Akun berhasil dibuat!", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, StarterMenu::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                                else{
                                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        else{
                            binding.etConfirmPassword.error = "Password tidak cocok"
                        }
                    }
                    else{
                        binding.etConfirmPassword.error = "Konfirmasi ulang password anda"
                    }
                }
                else{
                    binding.etPassword.error = "Field wajib diisi"
                }
            }
            else{
                binding.etEmail.error = "Field wajib diisi"
            }
        }

        binding.textView.setOnClickListener{
            val intent = Intent(this, StarterMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}