package com.restoran.listmakanan.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challengechapterenam.datastore.UserManager
import com.bumptech.glide.Glide
import com.restoran.listmakanan.R
import com.restoran.listmakanan.room.FavoriteDB
import com.restoran.listmakanan.room.FavoriteMakanan
import com.restoran.listmakanan.view.adapter.AdapterFavorite
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.android.synthetic.main.fragment_home_restoran.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class FavoriteRestoran : Fragment() {
    var db: FavoriteDB? = null
    var film : FavoriteMakanan? = null
    lateinit var userManager : UserManager
    lateinit var adapterFavorite : AdapterFavorite
    lateinit var emailUser : String
    var idUser by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        db = FavoriteDB.getInstance(requireContext())
        userManager = UserManager(requireContext())
        userManager.userImage.asLiveData().observe(requireActivity()){
            if (it !="x") {
                Glide.with(requireContext()).load(it).into(view.profilefav)
            }
        }
        emailUser =""

        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.welcomefav.text = it.toString()
        }

        userManager.userID.asLiveData().observe(requireActivity()){
            idUser = it.toInt()
        }
        userManager.userEmail.asLiveData().observe(requireActivity()){
            emailUser = it

            GlobalScope.launch {
//            film = db?.getFavoriteDao()?.getFilmID(id)!!
                db?.getFavoriteDao()?.getAllFav()
                Log.d("www", emailUser)
                val listdata = db?.getFavoriteDao()?.getFav(it)
                requireActivity().runOnUiThread {
                    listdata.let {
                        if (listdata?.size == 0) {
                            checkdatafav.text = "Belum ada favorite"
                        }
                        adapterFavorite = AdapterFavorite(){
                            val bund = Bundle()
                            bund.putParcelable("detailfilmfromfav", it)
                            view.findNavController().navigate(R.id.action_favoriteRestoran_to_detailRestoran,bund)
                        }
                        view.listfav.adapter = adapterFavorite
                        adapterFavorite.setDataFav(it!!)
                    }
                }
            }

        }
        view.listfav.layoutManager = LinearLayoutManager(requireContext())

        view.profilefav.setOnClickListener {
            view.findNavController().navigate(R.id.action_favoriteRestoran_to_profileFragment)
        }
        return view
    }

}