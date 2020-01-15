package com.innocyber.instachat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.innocyber.instachat.Model.User
import com.innocyber.instachat.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.provider.PicassoProvider
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.user_item_layout.view.*

class UserAdapter(private  var mContext: Context, private var mUser: List<User>, private var isFragment: Boolean = false): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_item_layout,parent,false)
        return UserAdapter.ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user = mUser[position]
        holder.userNameTextView.text = user.getUsername()
        holder.userFullnameTextView.text = user.getFullname()
        PicassoProvider.get().load(user.getImage()).placeholder(R.drawable.profile).into(holder.userProfileImage)
        //holder.userNameTextView.text = user.getUsername()

        checkFollowingStatus(user.getUid(),holder.followButton)

        holder.followButton.setOnClickListener {

            if(holder.followButton.text.toString() == "Follow"){
                firebaseUser?.uid.let { it1 ->
                FirebaseDatabase .getInstance().reference
                    .child("Follow").child(it1.toString())
                    .child("Following").child(user.getUid())
                    .setValue(true).addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            firebaseUser?.uid.let { it2 ->
                                FirebaseDatabase .getInstance().reference
                                    .child("Follow").child(user.getUid())
                                    .child("Followers").child(it1.toString())
                                    .setValue(true).addOnCompleteListener{task ->
                                        if (task.isSuccessful){

                                        }
                                    }
                            }
                        }
                    }}
            }else{
                firebaseUser?.uid.let { it1 ->
                    FirebaseDatabase .getInstance().reference
                        .child("Follow").child(it1.toString())
                        .child("Following").child(user.getUid())
                        .removeValue().addOnCompleteListener{task ->
                            if (task.isSuccessful){
                                firebaseUser?.uid.let { it2 ->
                                    FirebaseDatabase .getInstance().reference
                                        .child("Follow").child(user.getUid())
                                        .child("Followers").child(it1.toString())
                                        .removeValue().addOnCompleteListener{task ->
                                            if (task.isSuccessful){

                                            }
                                        }
                                }
                            }
                } }
            }
        }
    }

    private fun checkFollowingStatus(uid: String, followButton: Button) {
        val followingRef = firebaseUser?.uid.let { it1 ->
            FirebaseDatabase .getInstance().reference
                .child("Follow").child(it1.toString())
                .child("Following")
        }
        followingRef.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(uid).exists()){
                    followButton.text = "Following"
                }else{
                    followButton.text = "Follow"
                }
            }

        })

    }

    class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        var userNameTextView: TextView = itemView.findViewById(R.id.username_search)
        var userFullnameTextView: TextView = itemView.findViewById(R.id.full_name_search)
        var userProfileImage: CircleImageView = itemView.findViewById(R.id.user_profile_image_search)
        var followButton: Button = itemView.findViewById(R.id.follow_button_search)

    }
}