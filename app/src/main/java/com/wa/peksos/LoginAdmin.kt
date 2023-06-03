package com.wa.peksos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.wa.peksos.databinding.ActivityLoginUserBinding

class LoginAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUserBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login_admin)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
    }
}