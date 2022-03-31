package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
    // класс который хранит созданные view, и только для них выполняется метод findViewById
    // т.о. решена вторая проблема  медленный метод findViewById выполняется тольно для
    // показываемыз элементов view
    val tvName =view.findViewById<TextView>(R.id.tv_name)
    val tvCount = view.findViewById<TextView>(R.id.tv_count)
}