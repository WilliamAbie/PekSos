package com.wa.peksos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wa.peksos.databinding.ActivityMenuUtamaAdminBinding

class MenuUtamaAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityMenuUtamaAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuUtamaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(FragmentHomeAdmin())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.home -> replaceFragment(FragmentHomeAdmin())
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