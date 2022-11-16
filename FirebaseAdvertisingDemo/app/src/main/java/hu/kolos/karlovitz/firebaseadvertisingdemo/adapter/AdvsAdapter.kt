package hu.kolos.karlovitz.firebaseadvertisingdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import hu.kolos.karlovitz.firebaseadvertisingdemo.data.Adv
import hu.kolos.karlovitz.firebaseadvertisingdemo.databinding.AdvRowBinding
import java.text.SimpleDateFormat
import java.util.*

class AdvsAdapter : RecyclerView.Adapter<AdvsAdapter.ViewHolder> {

    lateinit var context: Context
    var  advsList = mutableListOf<Adv>()
    var  advKeys = mutableListOf<String>()

    lateinit var currentUid: String

    constructor(context: Context, uid: String) : super() {
        this.context = context
        this.currentUid = uid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdvRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return advsList.size
    }

    // Minden elemnél meghívódik!!
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var adv = advsList.get(holder.adapterPosition)

        holder.tvAuthor.text = adv.author
        holder.tvTitle.text = adv.title
        holder.tvText.text = adv.text
        holder.tvPrice.text = adv.price.toString()
        holder.tvEmail.text = adv.email
        holder.tvPhone.text = adv.phone
        holder.tvTimeStamp.text = SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(adv.timeStamp))

        if (currentUid == adv.uid) {
            holder.btnDelete.visibility = View.VISIBLE
            holder.btnEdit.visibility  = View.VISIBLE
        } else {
            holder.btnDelete.visibility = View.GONE
            holder.btnEdit.visibility = View.GONE
        }

        holder.btnDelete.setOnClickListener {
            removeAdv(holder.adapterPosition)
        }
    }

    fun addAdv(adv: Adv, key: String) {
        advsList.add(adv)
        advKeys.add(key)
        //notifyDataSetChanged()
        // Csak az adott helyen frissíti a listát!!
        notifyItemInserted(advsList.lastIndex)
    }

    // when I remove the post object
    private fun removeAdv(index: Int) {
        FirebaseFirestore.getInstance().collection("advs").document(
            advKeys[index]
        ).delete()

        advsList.removeAt(index)
        advKeys.removeAt(index)
        notifyItemRemoved(index)
    }

    // when somebody else removes an object
    fun removeAdvByKey(key: String) {
        val index = advKeys.indexOf(key)
        if (index != -1) {
            advsList.removeAt(index)
            advKeys.removeAt(index)
            // A törölt elem része lesz frissítve a listában!
            notifyItemRemoved(index)
        }
    }


    inner class ViewHolder(val binding: AdvRowBinding) : RecyclerView.ViewHolder(binding.root){
        var tvAuthor = binding.tvAuthor
        var tvTitle = binding.tvTitle
        var tvText = binding.tvText
        var tvPrice = binding.tvPrice
        var tvEmail = binding.tvEmail
        var tvPhone = binding.tvPhone
        var tvTimeStamp = binding.tvTimeStamp

        var btnDelete = binding.btnDelete
        var btnEdit = binding.btnEdit
    }
}