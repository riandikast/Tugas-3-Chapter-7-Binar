package com.restoran.listmakanan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.listfilm.andika.model.update.UpdateResponse

import com.restoran.listmakanan.model.makanan.GetMenuItem
import com.restoran.listmakanan.model.update.UpdateMenuResponse
import com.restoran.listmakanan.model.user.GetUserItem
import com.restoran.listmakanan.network.ApiClient
import retrofit2.Call
import retrofit2.Response


class ViewModelMakanan (): ViewModel() {
    var liveDataMenu : MutableLiveData<List<GetMenuItem>> = MutableLiveData()
    var liveDataUpdateMenu : MutableLiveData<GetMenuItem> = MutableLiveData()

    fun getLiveMenuObserver() : MutableLiveData<List<GetMenuItem>> {
        return liveDataMenu
    }

    fun getLiveMenuUpdateObserver() : MutableLiveData<GetMenuItem> {
        return liveDataUpdateMenu
    }



    fun getMenu(){
        ApiClient.instance.getMenu()
            .enqueue(object : retrofit2.Callback<List<GetMenuItem>>{
                override fun onResponse(
                    call: Call<List<GetMenuItem>>,
                    response: Response<List<GetMenuItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataMenu.postValue(response.body())

                    }else{
                        liveDataMenu.postValue(null)

                    }
                }

                override fun onFailure(call: Call<List<GetMenuItem>>, t: Throwable) {
                    liveDataMenu.postValue(null)
                }
            })
            }

    fun updateDataMenu(id : Int, dataUser : UpdateMenuResponse){
        ApiClient.instance.updateMenu(dataUser, id.toString() )
            .enqueue(object : retrofit2.Callback<GetMenuItem> {
                override fun onResponse(
                    call: Call<GetMenuItem>,
                    response: Response<GetMenuItem>
                ) {
                    if (response.isSuccessful){
                        liveDataUpdateMenu.postValue(response.body())

                    }else{
                        liveDataUpdateMenu.postValue(null)

                    }

                }
                override fun onFailure(call: Call<GetMenuItem>, t: Throwable) {
                    liveDataUpdateMenu.postValue(null)
                }
            })
    }


}

