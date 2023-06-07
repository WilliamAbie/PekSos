package com.wa.peksos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class FragmentCatatanAdmin : Fragment() {

    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catatan_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivBack = view.findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            val fragment = FragmentHomeUser()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentLayout, fragment)?.commit()
        }

        val tvDay = view.findViewById<TextView>(R.id.tvDayCatatan)
        tvDay.text = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

        val tvMonth = view.findViewById<TextView>(R.id.tvMonthCatatan)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1

        if (month == 1){ // kondisi untuk mengubah integer bulan menjadi string
            tvMonth.text = "Januari"
        }
        else if (month == 2){
            tvMonth.text = "Februari"
        }
        else if (month == 3){
            tvMonth.text = "Maret"
        }
        else if (month == 4){
            tvMonth.text = "April"
        }
        else if (month == 5){
            tvMonth.text = "Mei"
        }
        else if (month == 6){
            tvMonth.text = "Juni"
        }
        else if (month == 7){
            tvMonth.text = "Juli"
        }
        else if (month == 8){
            tvMonth.text = "Agustus"
        }
        else if (month == 9){
            tvMonth.text = "September"
        }
        else if (month == 10){
            tvMonth.text = "Oktober"
        }
        else if (month == 11){
            tvMonth.text = "November"
        }
        else if (month == 12){
            tvMonth.text = "Desember"
        }

        val etEmailCatatan = view.findViewById<EditText>(R.id.etEmailCatatan)
        val btnCari = view.findViewById<Button>(R.id.btnCari)
        val tvCatatan = view.findViewById<TextView>(R.id.tvCatatan)
        firebaseDatabase = FirebaseDatabase.getInstance()

        btnCari.setOnClickListener {
            val emailUser = etEmailCatatan.text.toString()
            val safeEmail = emailUser.replace(".",",")
            firebaseDatabase.getReference("catatan").child(safeEmail).get()
                .addOnSuccessListener {
                    if (it.exists()){
                        tvCatatan.text = it.value.toString()
                    }
                }
        }
    }
}