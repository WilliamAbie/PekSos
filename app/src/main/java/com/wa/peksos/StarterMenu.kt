package com.wa.peksos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wa.peksos.databinding.ActivityStarterMenuBinding

class StarterMenu : AppCompatActivity() {

    private lateinit var binding: ActivityStarterMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStarterMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnUser.setOnClickListener {
            val intent = Intent(this, LoginUser::class.java)
            startActivity(intent)
        }

        binding.btnAdmin.setOnClickListener {
            val intent = Intent(this, LoginAdmin::class.java)
            startActivity(intent)
        }
    }
}