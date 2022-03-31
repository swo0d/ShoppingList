package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {

    //  создаем viewModel
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListadapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        // присваиваем значение viewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // подписываемся на обьект shopList
        viewModel.shopList.observe(this) {
            Log.d("MainActivityTest", it.toString())
            // shopListadapter.shopList = it
            //  при работе с ListAdapter
            shopListadapter.submitList(it)
        }
    }
    // конфигурируем RecyclerView
    private fun setupRecyclerView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)


        with(rvShopList) {
            shopListadapter = ShopListAdapter()
            adapter = shopListadapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupOnClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        // для свайпа (swipe) создаем callback
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            // метод onMove здесь нам не нужен, возвращаеа false
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // здесь нам надо получить элемент по которому прошел свайп и удалить его из нашей коллекции
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // получаем нашу коллекцию shopListAdapter.shopList и позицию элемента -> получаем из viewHolder
                //val item = shopListadapter.shopList[viewHolder.bindingAdapterPosition]
                // чтобы вызвать текущий список элементов в адаптере можно вызвать currentList
                val item = shopListadapter.currentList[viewHolder.bindingAdapterPosition]
                // удаляем полученный элемент
                viewModel.deleteShopItem(item)
            }

        }
        // создаем объект itemTouchHelper
        val itemTouchHelper = ItemTouchHelper(callback)
        // прикрепляем этот хелпер itemTouchHelper к RecyclerView
        itemTouchHelper.attachToRecyclerView((rvShopList))
    }

    private fun setupOnClickListener() {
        shopListadapter.onShopItemClickListener = {
            // viewModel.editShopItem(it)
            Log.d("MainActivity", it.toString())
        }
    }

    private fun setupLongClickListener() {
        shopListadapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


}