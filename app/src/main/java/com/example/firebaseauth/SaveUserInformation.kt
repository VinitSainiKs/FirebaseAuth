package com.example.firebaseauth

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_save_user_information.*

const val CHOOSE_IMAGE = 101

class SaveUserInformation : AppCompatActivity() {
    lateinit var uriProfilePic: Uri
    lateinit var storageRef: StorageReference
//    lateinit var profileImageUrl: Task<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_user_information)

        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)

        iv_uploadProfile.setOnClickListener {
            imageChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            uriProfilePic = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriProfilePic)
                iv_uploadProfile.setImageBitmap(bitmap)
                uploadImage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        println("uploadImage In")
        storageRef = FirebaseStorage.getInstance().reference
        val uploadTask = storageRef.child("images/rev.jpg")
            .putFile(uriProfilePic)
        println("storageRef $storageRef")
        /*uploadTask.addOnProgressListener {
            val progress = (100.0 * it.bytesTransferred)/it.totalByteCount
            println("Upload is $progress% done")
        }
            .addOnPausedListener {
                println("Upload is paused")
            }*/
            uploadTask.addOnSuccessListener {
                println("uploadTask.addOnSuccessListener")
//                profileImageUrl = storageRef.downloadUrl
//                println("profileImageUrl -->> $profileImageUrl")
            }
            .addOnFailureListener {
                println("Error -->> ${it.printStackTrace()}")
            }
    }

    fun imageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, CHOOSE_IMAGE)
    }
}
