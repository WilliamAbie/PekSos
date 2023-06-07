package com.wa.peksos

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Calendar

class FragmentHomeUser : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_home_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        (activity as AppCompatActivity).supportActionBar?.hide() // menghilangkan bar biru diatas pada fragment

        val tvDay = view.findViewById<TextView>(R.id.tvDay)
        tvDay.text = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            .toString() // mengambil tanggal saat ini

        val tvMonth =
            view.findViewById<TextView>(R.id.tvMonth) // mengambil int bulan sekarang dan menggantinya menjadi string dengan kondisi if else
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        if (month == 1) {
            tvMonth.text = "Januari"
        } else if (month == 2) {
            tvMonth.text = "Februari"
        } else if (month == 3) {
            tvMonth.text = "Maret"
        } else if (month == 4) {
            tvMonth.text = "April"
        } else if (month == 5) {
            tvMonth.text = "Mei"
        } else if (month == 6) {
            tvMonth.text = "Juni"
        } else if (month == 7) {
            tvMonth.text = "Juli"
        } else if (month == 8) {
            tvMonth.text = "Agustus"
        } else if (month == 9) {
            tvMonth.text = "September"
        } else if (month == 10) {
            tvMonth.text = "Oktober"
        } else if (month == 11) {
            tvMonth.text = "November"
        } else if (month == 12) {
            tvMonth.text = "Desember"
        }

        val tvEmail = view.findViewById<TextView>(R.id.tvEmail) // mengambil data email
        tvEmail.text = firebaseAuth.currentUser?.email.toString()

        val ivProfile =
            view.findViewById<CircleImageView>(R.id.ivProfile) // mengambil data foto profile dari cloud storage
        val imageRef = firebaseStorage.reference.child("/img/${firebaseAuth.currentUser?.uid}")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            Picasso.get().load(imageUrl)
                .into(ivProfile) // library picasso untuk mengambil foto melalui url
        }
        ivProfile.setOnClickListener { // pindah halaman saat foto profil ditekan
            val fragment = FragmentProfile()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentLayout, fragment)?.commit()
        }

        val tvJadwal = view.findViewById<TextView>(R.id.tvJadwal) // mengambil data dari realtime database
        val emailUser = firebaseAuth.currentUser?.email
        val safeEmail = emailUser?.replace(".",",")
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.getReference("kunjungan")
            .child("${safeEmail}").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    tvJadwal.text = it.value.toString()
                }
            }

        val calendarButton = view.findViewById<ImageView>(R.id.ivCalendar)
        calendarButton.setOnClickListener{
            showDatePicker()
        }

        val cvCatatan = view.findViewById<CardView>(R.id.cvCatatan)
        cvCatatan.setOnClickListener {
            val fragment = FragmentCatatanUser()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentLayout, fragment)?.commit()
        }

    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val initYear = calendar.get(Calendar.YEAR)
        val initMonth = calendar.get(Calendar.MONTH)
        val initDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->},
            initYear, initMonth, initDay)

        datePickerDialog.show()
    }

    private fun Fragment.makeToast(text: String,duration: Int = Toast.LENGTH_LONG) {
        activity?.let {
            Toast.makeText(it, text, duration).show()
        }
    }
}