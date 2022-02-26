package com.mainway.store.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mainway.store.R
import com.mainway.store.common.NikeFragment
import com.mainway.store.feature.auth.AuthActivity
import com.mainway.store.feature.favorite.FavoriteProductsActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : NikeFragment() {

    val viewModel:ProfileViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteProductsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkOutState()
    }
    private fun checkOutState(){
        if (viewModel.isSignIn){
            authBtn.text=getString(R.string.signOut)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_out,0)
            userNameTv.text=viewModel.userName
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkOutState()
            }
        }else{
            authBtn.text=getString(R.string.loginScreenTitle)
            authBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_in,0)
            userNameTv.text=getString(R.string.guest_user)
            authBtn.setOnClickListener {
               startActivity(Intent(requireContext(),AuthActivity::class.java))
            }
        }
    }



}