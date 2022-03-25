package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// дата и presentation слой не должны знать друг о друге
import com.example.shoppinglist.data.ShopListRepositoryImpl

import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

   // val shopList = MutableLiveData<List<ShopItem>>()
    val shopList = getShopListUseCase.getShopList()

    //  fun getShopList(){
    //    val list = getShopListUseCase.getShopList()
        //вставляем в нашу LiveData, есть два способа:
        // .value вызывается из главного потока und .postvalue вызывается из любого потока
   //     shopList.value = list
        //т.о. мы получим список элентов из нашего UseCase и запишем их в нашу LiveData, а из активити
        // мы можем подписмться на нашу LiveData val shopList и отобразить список.
   // }

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
        // автоматически абновляем список элементов
        //getShopList()
    }

    fun changeEnableState(shopItem: ShopItem) {
        // Здесь будет создана полная копия данного обьекта но с переопределенным свойством enabled
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        // изменяем данный обьект
        editShopItemUseCase.editShopItem(newItem)
        // автоматически абновляем список элементов
       // getShopList()
    }

}