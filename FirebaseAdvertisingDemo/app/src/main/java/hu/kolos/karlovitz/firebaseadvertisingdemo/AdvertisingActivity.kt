package hu.kolos.karlovitz.firebaseadvertisingdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.kolos.karlovitz.firebaseadvertisingdemo.adapter.AdvsAdapter
import hu.kolos.karlovitz.firebaseadvertisingdemo.data.Adv
import hu.kolos.karlovitz.firebaseadvertisingdemo.databinding.ActivityAdvertisingBinding

class AdvertisingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdvertisingBinding

    private lateinit var advsAdapter: AdvsAdapter

    // Ez figyeli a változásokat
    private lateinit var eventListener: EventListener<QuerySnapshot>
    // Ez mutat rá a collectionra
    private lateinit var queryRef: CollectionReference
    // Erre az objektumra kell fel, meg leiratkozni
    private var listenerReg: ListenerRegistration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdvertisingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = title

        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this, CreateAdvActivity::class.java))
        }

        // Az adapter összekötése a RecyclerView-al
        advsAdapter = AdvsAdapter(
            this,
            FirebaseAuth.getInstance().currentUser!!.uid
        )
        binding.recyclerAdvs.adapter = advsAdapter

        initFirebaseQuery()
    }

    private fun initFirebaseQuery() {
        /*FirebaseFirestore.getInstance().collection(CreatePostActivity.COLLECTION_POSTS)
            .whereEqualTo("title", "demo")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val post = document.toObject(Post::class.java)
                    postsAdapter.addPost(post, document.id)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this@ForumActivity, "Error: ${exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }*/

        val query = FirebaseFirestore.getInstance().collection(CreateAdvActivity.COLLECTION_ADVS)
            .whereEqualTo("title", "demo")



        queryRef = FirebaseFirestore.getInstance().collection(CreateAdvActivity.COLLECTION_ADVS)

        eventListener = object : EventListener<QuerySnapshot> {
            // Meghívódik, ha változás történt.
            override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                if (e != null) {
                    Toast.makeText(
                        this@AdvertisingActivity, "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val adv = docChange.document.toObject(Adv::class.java)
                        advsAdapter.addAdv(adv, docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                        advsAdapter.removeAdvByKey(docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                    }
                }

            }
        }
        // Az Activity a regisztrációt elvégzi:
        listenerReg = queryRef.addSnapshotListener(eventListener)
    }

    // Az Activity megsemmisülésekor le kell iratkozni:
    override fun onDestroy() {
        super.onDestroy()
        listenerReg?.remove()
    }
}