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
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Calendar

class FragmentHome : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        val tvMonth = view.findViewById<TextView>(R.id.tvMonth)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1

        if (month == 1){
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

        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        tvEmail.text = FirebaseAuth.getInstance().currentUser?.email.toString()

        val ivProfile = view.findViewById<CircleImageView>(R.id.ivProfile)
        ivProfile.setOnClickListener {
            val fragment = FragmentProfile()
            val transaction = fragmentManager?.beginTransaction()

            transaction?.replace(R.id.fragmentLayout, fragment)?.commit()
        }

        val calendarButton = view.findViewById<ImageView>(R.id.ivCalendar)

        calendarButton.setOnClickListener {
            showDatePickerDialog()
        }
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
}