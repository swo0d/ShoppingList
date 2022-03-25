package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    // коллекция элементов которая будет отображаться
    var shopList = listOf<ShopItem>()
    set(value) {
        field =value
        notifyDataSetChanged()
    }
    // как создавать view Вызывается в момент скрола, создает необходимое кол-во view
    // элементов для показа на экране и еще несколько сверху и снизу
    // проблема метода inflate который вызывается теперь только для показываемых view - решена
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        // создаеm view из макета например: item_shop_disabled
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_disabled,
            parent,
            false
        )
        // передаем созданное view в ShopItemViewHolder, внутри этого объекта дважды вызовется
        // метод findViewById и мы получим необходимые элементы в которых можно выставлять значения
        return ShopItemViewHolder(view)
    }
    // как ... какие тексты вставить. В момент скрола для каждого элемента будет вызван метод
    // onBindViewHolder, туда будет прилетать созданный viewHolder и позиция созданного элемента,
    // по этой позиции получаем элемент из списка и устанавливаем необходимые тексты для полученных
    // элементов
    override fun onBindViewHolder(viewholder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if (shopItem.enabled) {
            "Active"
        }else {
            "Not Active"
        }
        viewholder.view.setOnLongClickListener{
            true
        }
        if (shopItem.enabled) {
            viewholder.tvName.text = "${shopItem.name} $status"
            viewholder.tvCount.text = shopItem.count.toString()
            viewholder.tvName.setTextColor(ContextCompat.getColor(viewholder.view.context,android.R.color.holo_red_dark))
        }else {
            viewholder.tvName.text = "${shopItem.name} $status"
            viewholder.tvCount.text = shopItem.count.toString()
            viewholder.tvName.setTextColor(ContextCompat.getColor(viewholder.view.context,android.R.color.white))
        }
    }




    override fun getItemCount(): Int {
        return  shopList.size
    }

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        // класс который хранит созданные view, и только для них выполняется метод findViewById
        // т.о. решена вторая проблема  медленный метод findViewById выполняется тольно для
        // показываемыз элементов view
        val tvName =view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

}