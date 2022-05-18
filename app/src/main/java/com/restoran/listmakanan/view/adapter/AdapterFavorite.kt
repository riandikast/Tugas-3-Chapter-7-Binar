package com.restoran.listmakanan.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.restoran.listmakanan.R

import com.restoran.listmakanan.room.FavoriteMakanan
import kotlinx.android.synthetic.main.item_fav.view.*


class AdapterFavorite (var onclick : (FavoriteMakanan)-> Unit) : RecyclerView.Adapter<AdapterFavorite.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    var datafav : List<FavoriteMakanan>? = null
    fun setDataFav(fav  : List<FavoriteMakanan>){
        this.datafav = fav
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Glide.with(holder.itemView.context).load(datafav!![position].image).into(holder.itemView.gambarv)

        holder.itemView.text1v.text = datafav!![position].namaMakanan
        holder.itemView.text2v.text = datafav!![position].harga


        holder.itemView.cardv.setOnClickListener {
            onclick(datafav!![position])
        }
    }

    override fun getItemCount(): Int {
        if (datafav == null){
            return 0
        }else{
            return datafav!!.size
        }
    }
}