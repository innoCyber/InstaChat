package com.innocyber.instachat.Fragments.profile


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.innocyber.instachat.AccountSettingsActivity

import com.innocyber.instachat.R
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var profileId: String
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        if (pref != null){
            this.profileId = pref.getString("profileId","none")
        }

        if(profileId == firebaseUser.uid){

            view.edit_account_settings.text = "Edit Profile"
        }

        else if (profileId == firebaseUser.uid){
            checkFollowAndFollowing()
        }

        view.edit_account_settings.setOnClickListener{
            startActivity(Intent(context,AccountSettingsActivity::class.java))
        }

        return view
    }

    private fun checkFollowAndFollowing() {

    }


}
