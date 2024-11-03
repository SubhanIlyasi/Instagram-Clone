package com.example.instagram.Utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, foldername: String, callback: (String?) -> Unit) {
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                callback(imageUrl)
            }
        }
        .addOnFailureListener {
            callback(null)
        }
}

fun uploadVideo(
    uri: Uri,
    foldername: String,
    progressDialog: ProgressDialog,
    callback: (String?) -> Unit
) {
    progressDialog.setTitle("Uploading Video . . .")
    progressDialog.show()
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                val videoUrl = uri.toString()
                progressDialog.dismiss()
                callback(videoUrl)
            }
        }
        .addOnProgressListener {
            var uploadedValue: Long = it.bytesTransferred / it.totalByteCount
            progressDialog.setMessage("Uploaded $uploadedValue %")
        }
        .addOnFailureListener {
            callback(null)
        }

}
