package com.restoran.listmakanan.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.restoran.listmakanan.R
import com.restoran.listmakanan.model.makanan.GetMenuItem
import com.restoran.listmakanan.room.FavoriteDB
import com.restoran.listmakanan.room.FavoriteMakanan
import kotlinx.android.synthetic.main.fragment_detail_restoran.*
import kotlinx.android.synthetic.main.fragment_detail_restoran.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.properties.Delegates


class DetailRestoran : Fragment() {
    var db: FavoriteDB? = null
    var film : List<FavoriteMakanan>? = null

    lateinit var id : String
    lateinit var title : String
    lateinit var harga : String
    lateinit var email : String
    lateinit var createdAt : String
    lateinit var gambar : String
    lateinit var desc : String

    lateinit var userManager : com.binar.challengechapterenam.datastore.UserManager

    lateinit var toggleFavorite : String
    var alreadyFavorite by Delegates.notNull<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail_restoran, container, false)
        userManager = com.binar.challengechapterenam.datastore.UserManager(requireContext())
        val getfilm = arguments?.getParcelable<GetMenuItem>("detailfilm")
        val getfilmfromfav = arguments?.getParcelable<FavoriteMakanan>("detailfilmfromfav")
        db = FavoriteDB.getInstance(requireActivity())

        if (getfilm != null){
            view.text1.text = getfilm.namaMakanan
            view.text2.text = getfilm.harga
            view.text4.text = getfilm.desc
            Glide.with(requireContext()).load(getfilm.gambar).into(view.gambar1)
            id = getfilm.id
            title = getfilm.namaMakanan
            harga = getfilm.harga
            createdAt = getfilm.createdAt
            gambar = getfilm.gambar
            desc = getfilm.desc
        }

        if (getfilmfromfav != null){
            view.text1.text = getfilmfromfav.namaMakanan
            view.text2.text = getfilmfromfav.harga
            view.text4.text = getfilmfromfav.desc
            Glide.with(requireContext()).load(getfilmfromfav.gambar).into(view.gambar1)
            id = getfilmfromfav.id
            title = getfilmfromfav.namaMakanan
            harga = getfilmfromfav.harga
            createdAt = getfilmfromfav.createdAt
            gambar = getfilmfromfav.gambar
            desc = getfilmfromfav.desc
        }

        email = ""
        toggleFavorite = "false"
        alreadyFavorite = false

        userManager.userEmail.asLiveData().observe(requireActivity()){
            email = it
            GlobalScope.async {
                film = db?.getFavoriteDao()?.checkFav(email, id.toInt())
                val checkDB = film?.size !=0
                if (checkDB){
                    view.btnfavorite.setImageResource(R.drawable.love)
                    toggleFavorite = "true"
                    alreadyFavorite = true
                    //codebyandika
                }else {
                    view.btnfavorite.setImageResource(R.drawable.unlove )
                    toggleFavorite = "false"
                    alreadyFavorite = false
                }
            }
        }


        view.btnfavorite.setOnClickListener {
            toggleButton()
        }






        return view
    }

    fun toggleButton(){
        for (data in toggleFavorite){
            if (toggleFavorite == "true"  ){
                btnfavorite.setImageResource(R.drawable.unlove)
                toggleFavorite = "false"
                GlobalScope.async {
                    db?.getFavoriteDao()?.deleteMakananID(email, id.toInt())
                }
                break
            }

            if (toggleFavorite == "false"  ) {
                btnfavorite.setImageResource(R.drawable.love)
                toggleFavorite= "true"
                GlobalScope.async {
                    db?.getFavoriteDao()?.addMakanan(
                        FavoriteMakanan(
                            null,
                            email,
                            createdAt,
                            harga,
                            id,
                            title,
                            gambar,
                            desc
                        )
                    )
                }
                break
            }
        }
    }

}