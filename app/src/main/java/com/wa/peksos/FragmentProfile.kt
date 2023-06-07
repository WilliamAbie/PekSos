package com.wa.peksos

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class FragmentProfile : Fragment() {

    companion object{
        const val REQUEST_CAMERA = 100
    }
    private lateinit var imageUri : Uri
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            makeToast("Berhasil Log-out")
            val intent = Intent(activity,StarterMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivProfile : CircleImageView = view.findViewById(R.id.ivProfile)
        val tvEmail : TextView = view.findViewById(R.id.tvEmail)
        val tvUid : TextView = view.findViewById(R.id.tvUid)

        tvEmail.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        tvUid.text = FirebaseAuth.getInstance().currentUser?.uid.toString()

        firebaseStorage = FirebaseStorage.getInstance()
        val imageRef = firebaseStorage.reference.child("/img/${FirebaseAuth.getInstance().currentUser?.uid}")
        imageRef.downloadUrl.addOnSuccessListener {
                uri -> val imageUrl = uri.toString()
            Picasso.get().load(imageUrl).into(ivProfile)
        }

        ivProfile.setOnClickListener{
            intentCamera()
        }
    }

    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also{
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("/img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let{
                            imageUri = it
                            val ivProfile = view?.findViewById<CircleImageView>(R.id.ivProfile)
                            ivProfile?.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    private fun Fragment.makeToast(text: String,duration: Int = Toast.LENGTH_LONG) {
        activity?.let {
            Toast.makeText(it, text, duration).show()
        }
    }
}