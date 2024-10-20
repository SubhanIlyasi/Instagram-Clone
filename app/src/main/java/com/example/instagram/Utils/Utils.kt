package com.example.instagram.Utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, foldername: String, callback: (String?) -> Unit) {
    FirebaseStorage.getInstance().getReference(foldername).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                callback(imageUrl)  // Pass the image URL to the callback
            }
        }
        .addOnFailureListener {
            callback(null)  // Pass null to the callback in case of failure
        }
}
