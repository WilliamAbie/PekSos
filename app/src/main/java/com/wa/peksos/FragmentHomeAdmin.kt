package com.wa.peksos

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Calendar

class FragmentHomeAdmin : Fragment() {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        val tvDay = view.findViewById<TextView>(R.id.tvDay)
        tvDay.text = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

        val tvMonth = view.findViewById<TextView>(R.id.tvMonth)
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

        val tvEmail = view.findViewById<TextView>(R.id.tvEmail) // mengubah value email
        tvEmail.text = FirebaseAuth.getInstance().currentUser?.email.toString()

        val ivProfile = view.findViewById<CircleImageView>(R.id.ivProfile) // mengambil data image dari storage dan post ke gambar profil
        firebaseStorage = FirebaseStorage.getInstance()
        val imageRef = firebaseStorage.reference.child("/img/${FirebaseAuth.getInstance().currentUser?.uid}")
        imageRef.downloadUrl.addOnSuccessListener {
            uri -> val imageUrl = uri.toString()
            Picasso.get().load(imageUrl).into(ivProfile)
        }
        ivProfile.setOnClickListener { // membuat aksi perpindahan saat foto profil ditekan
            val fragment = FragmentProfile()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentLayout, fragment)?.commit()
        }

        val calendarButton = view.findViewById<ImageView>(R.id.ivCalendar) // aksi saat menekan icon kalendar diatas
        calendarButton.setOnClickListener {
            showDatePickerDialog()
        }

        val etEmail = view.findViewById<EditText>(R.id.etEmailPasien)
        val btnTanggal = view.findViewById<Button>(R.id.btnTanggal) // edit tanggal kunjungan
        btnTanggal.setOnClickListener {
            val email = etEmail.text.toString()
            val safeEmail = email.replace(".",",")
            showDatePicker(safeEmail)
        }

        val cvCatatan = view.findViewById<CardView>(R.id.cvCatatan)
        cvCatatan.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentLayout, FragmentCatatanAdmin())?.commit()
        }
    }

    private fun showDatePicker(email: String){ // datepicker kalendar
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        firebaseDatabase = FirebaseDatabase.getInstance()

        val ref = firebaseDatabase.getReference("kunjungan")
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = formatDate(year, month, dayOfMonth)
                ref.child(email).setValue("${selectedDate}")

                makeToast("Tanggal berhasil ditetapkan!")
            }, initialYear, initialMonth, initialDay)

        datePickerDialog.datePicker.minDate = calendar.timeInMillis // untuk meng-disable tanggal sebelum hari ini
        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = android.text.format.DateFormat.getDateFormat(requireContext())
        return dateFormat.format(calendar.time)
    }

    private fun showDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _: DatePicker, year: Int, month: Int, dayOfMonth: Int -> }, initialYear, initialMonth, initialDay)

        datePickerDialog.show()
    }

    private fun Fragment.makeToast(text: String,duration: Int = Toast.LENGTH_LONG) {
        activity?.let {
            Toast.makeText(it, text, duration).show()
        }
    }
}