package com.restoran.listmakanan.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challengechapterenam.datastore.UserManager
import com.restoran.listmakanan.R
import com.restoran.listmakanan.view.adapter.AdapterHome
import com.restoran.listmakanan.viewmodel.ViewModelMakanan
import kotlinx.android.synthetic.main.fragment_home_restoran.view.*


class HomeRestoran : Fragment() {
    lateinit var adaptermakanan : AdapterHome

    lateinit var userManager : UserManager
    lateinit var email : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_restoran, container, false)
        var homelinear = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //New Movie~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        adaptermakanan = AdapterHome(){
            val bund = Bundle()
            bund.putParcelable("detailfilm", it)
            view.findNavController().navigate(R.id.action_homeRestoran_to_detailRestoran,bund)

        }
        view.list.adapter = adaptermakanan
        view.list.layoutManager = homelinear

        getMakanan()
        view.profile.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeRestoran_to_profileFragment)
        }
        userManager = UserManager(requireContext())
        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.welcome.text = it.toString()
        }
        return view
    }

    fun getMakanan(){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelMakanan::class.java)
        viewModel.getLiveMenuObserver().observe(requireActivity()) {
            if(it != null){
                adaptermakanan.setDataFilm(it)
                adaptermakanan.notifyDataSetChanged()
            }
        }
        viewModel.getMenu()
    }


}