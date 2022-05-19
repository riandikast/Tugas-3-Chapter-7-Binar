package com.restoran.listmakanan.view.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.findNavController
import com.binar.challengechapterenam.datastore.UserManager
import com.restoran.listmakanan.R


class SplashScreen : Fragment() {
    lateinit var userManager : UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        userManager = UserManager(requireContext())
        Handler().postDelayed({
            userManager.userLogin.asLiveData().observe(requireActivity()){
                if (it.equals("false")){
                    view.findNavController().navigate(R.id.action_splashScreen_to_loginRestoran, null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreen,
                                true).build())

                }else if (it.equals("true")){
                    view.findNavController().navigate(R.id.action_splashScreen_to_homeRestoran, null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.splashScreen,
                                true).build())
                }
            }
        }, 2000)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}