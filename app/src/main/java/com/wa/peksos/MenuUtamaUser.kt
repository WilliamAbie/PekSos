package com.wa.peksos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wa.peksos.databinding.ActivityMenuUtamaUserBinding

class MenuUtamaUser : AppCompatActivity() {

    private lateinit var binding: ActivityMenuUtamaUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuUtamaUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(FragmentHome())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.home -> replaceFragment(FragmentHome())
                R.id.profile -> replaceFragment(FragmentProfile())
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentLayout, fragment)
        fragmentTransaction.commit()
    }
}