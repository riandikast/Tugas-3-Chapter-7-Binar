package com.restoran.listmakanan.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.listfilm.andika.model.update.UpdateResponse
import com.restoran.listmakanan.R
import com.restoran.listmakanan.model.makanan.GetMenuItem
import com.restoran.listmakanan.model.update.UpdateMenuResponse
import com.restoran.listmakanan.room.FavoriteMakanan
import com.restoran.listmakanan.viewmodel.ViewModelMakanan
import com.restoran.listmakanan.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_detail_restoran.view.*
import kotlinx.android.synthetic.main.fragment_edit_menu_restoran.*
import kotlinx.android.synthetic.main.fragment_edit_menu_restoran.view.*


class EditMenuRestoran : Fragment() {
    lateinit var id : String
    lateinit var nama : String
    lateinit var harga : String
    lateinit var desc : String
    lateinit var gambar : String
    lateinit var viewModel: ViewModelMakanan
    lateinit var dataUser : UpdateMenuResponse
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_menu_restoran, container, false)
        val getmakanan = arguments?.getParcelable<GetMenuItem>("detailfilm")
        val getmakananupdate = arguments?.getParcelable<UpdateMenuResponse>("updatemenu")
        val getmakananfromfav = arguments?.getParcelable<FavoriteMakanan>("detailfilmfromfav")

        if (getmakananfromfav != null && getmakananupdate == null){
            view.update1w.setText ( getmakananfromfav.namaMakanan)
            view.update2w.setText ( getmakananfromfav.harga )
            view.update3w.setText ( getmakananfromfav.gambar )
            view.update4w.setText ( getmakananfromfav.desc )
            Glide.with(requireContext()).load(getmakananfromfav.gambar).into(view.ppw)
            id = getmakananfromfav.id

        }
        if (getmakanan != null && getmakananupdate == null){
            view.update1w.setText ( getmakanan.namaMakanan)
            view.update2w.setText ( getmakanan.harga )
            view.update3w.setText ( getmakanan.gambar )
            view.update4w.setText ( getmakanan.desc )
            Glide.with(requireContext()).load(getmakanan.gambar).into(view.ppw)
            id = getmakanan.id

        }

        if (getmakananupdate!=null){
            view.update1w.setText ( getmakananupdate.namaMakanan)
            view.update2w.setText ( getmakananupdate.harga )
            view.update3w.setText ( getmakananupdate.gambar )
            view.update4w.setText ( getmakananupdate.desc )
            Glide.with(requireContext()).load(getmakananupdate.gambar).into(view.ppw)
            id = getmakananupdate.id.toString()
        }

        view.btnupdatew.setOnClickListener {
            nama = view.update1w.text.toString()
            harga  = view.update2w.text.toString()
            gambar = view.update3w.text.toString()
            desc = view.update4w.text.toString()
            dataUser = UpdateMenuResponse(id.toInt(), harga, nama, desc, gambar)
            updateDataMenu(id.toInt(), dataUser)
            val bund = Bundle()
            bund.putParcelable("updatemenu", dataUser)

            view.findNavController().navigate(R.id.action_editMenuRestoran_to_detailRestoran, bund)
        }
        return view
    }

    fun updateDataMenu(id : Int, dataUser : UpdateMenuResponse){
        viewModel = ViewModelProvider(this).get(ViewModelMakanan::class.java)
        viewModel.getLiveMenuUpdateObserver().observe(requireActivity(), Observer {
            if (it  == null){
                Toast.makeText(requireContext(), "Gagal Update Data", Toast.LENGTH_LONG ).show()
            }else{
                Toast.makeText(requireContext(), "Berhasil Update Data", Toast.LENGTH_LONG ).show()
            }
        })
        viewModel.updateDataMenu(id, dataUser)
    }

}