package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {

    //  создаем viewModel
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        // присваиваем значение viewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // подписываемся на обьект shopList
        viewModel.shopList.observe(this) {
            Log.d("MainActivityTest", it.toString())
            adapter.shopList = it

        }
    }
    // конфигурируем RecyclerView
    private fun setupRecyclerView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ShopListAdapter()
        rvShopList.adapter = adapter
    }


}