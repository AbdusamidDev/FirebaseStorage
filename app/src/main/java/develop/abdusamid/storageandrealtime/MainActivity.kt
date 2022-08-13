package develop.abdusamid.storageandrealtime

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import develop.abdusamid.storageandrealtime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var reference: StorageReference
    var imageUrl: String? =
        "https://firebasestorage.googleapis.com/v0/b/firebathrealtime.appspot.com/o/images%2F1660387064238?alt=media&token=cbb0d958-9e77-49ac-84a3-024e77a5577c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this).load(imageUrl).into(binding.imageView)
        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("images")
        binding.btnImage.setOnClickListener {
            getImageContent.launch("image/*")
        }
        binding.btnAudio.setOnClickListener {
            getImageContent.launch("audio/*")
        }
        binding.btnVideo.setOnClickListener {
            getImageContent.launch("video/*")
        }
    }

    private var getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val currenttime = System.currentTimeMillis()
            binding.progressBar.visibility = View.VISIBLE
            val task = reference.child(currenttime.toString()).putFile(uri!!)
            task.addOnSuccessListener {
                binding.imageView.setImageURI(uri)
                binding.progressBar.visibility = View.INVISIBLE
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
}