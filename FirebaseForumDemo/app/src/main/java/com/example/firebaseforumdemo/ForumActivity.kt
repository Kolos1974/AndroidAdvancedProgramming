package com.example.firebaseforumdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseforumdemo.adapter.PostsAdapter
import com.example.firebaseforumdemo.data.Post
import com.example.firebaseforumdemo.databinding.ActivityForumBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class ForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForumBinding

    private lateinit var postsAdapter: PostsAdapter

    private lateinit var eventListener: EventListener<QuerySnapshot>
    private lateinit var queryRef: CollectionReference
    private var listenerReg: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this, CreatePostActivity::class.java))
        }

        postsAdapter = PostsAdapter(
            this,
            FirebaseAuth.getInstance().currentUser!!.uid
        )
        binding.recyclerPosts.adapter = postsAdapter


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

        val query = FirebaseFirestore.getInstance().collection(CreatePostActivity.COLLECTION_POSTS)
            .whereEqualTo("title", "demo")



        queryRef = FirebaseFirestore.getInstance().collection(CreatePostActivity.COLLECTION_POSTS)

        eventListener = object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                if (e != null) {
                    Toast.makeText(
                        this@ForumActivity, "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val post = docChange.document.toObject(Post::class.java)
                        postsAdapter.addPost(post, docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                        postsAdapter.removePostByKey(docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                    }
                }

            }
        }

        listenerReg = queryRef.addSnapshotListener(eventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerReg?.remove()
    }
}