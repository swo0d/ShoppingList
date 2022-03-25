package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository{

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    // будем хранить все в переменной, все данные пропадут если выключить телефон
    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})


   // private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    // добавим для теста несколько элементов
    init {
        for (i in 0 until 1000) {
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            // operator  postincrement
            shopItem.id =  autoIncrementId++
        }

        //autoIncrementId++
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        // сначало удаляем старый элемент
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        // добавляем новый элемент
        addShopItem(shopItem)

    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        }?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
       return shopListLD
    }

    private fun updateList(){
        shopListLD.value = shopList.toList()
    }

}