package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

//class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
class ShopListAdapter: ListAdapter<ShopItem,ShopItemViewHolder>(ShopItemDiffCallback()){

    var count = 0
    /*закомментируем для ListAdapter
    // коллекция элементов которая будет отображаться
    var shopList = listOf<ShopItem>()
    set(value) {
        // создаем объект ShopListDiffCallback который реализует методы для сравнения списков
        val callback = ShopListDiffCallback(shopList, value)    // передаем старый и новый списки
        // произвлодим вычисления чтобы узнать какие изменения произошли в списке
        // в diffResult хранятся все изменения которые необходимо произвести в адаптаре
        val diffResult = DiffUtil.calculateDiff(callback)
        // производим изменения
        diffResult.dispatchUpdatesTo(this)
        // обновляем список shopList
        field =value
       // notifyDataSetChanged()
    }

 */
    var onShopItemLongClickListener: ((ShopItem) -> Unit)?= null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    // как создавать view Вызывается в момент скрола, создает необходимое кол-во view
    // элементов для показа на экране и еще несколько сверху и снизу
    // проблема метода inflate который вызывается теперь только для показываемых view - решена
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder, count:${++count}")
         // выбор viewType и соответствующий layout макет
        val layout = when (viewType) {
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED ->  R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        // создаем view из макета например: item_shop_disabled
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        // передаем созданное view в ShopItemViewHolder, внутри этого объекта дважды вызовется
        // метод findViewById и мы получим необходимые элементы в которых можно выставлять значения
        return ShopItemViewHolder(view)
    }
    // как ... какие тексты вставить. В момент скрола для каждого элемента будет вызван метод
    // onBindViewHolder, туда будет прилетать созданный viewHolder и позиция созданного элемента,
    // по этой позиции получаем элемент из списка и устанавливаем необходимые тексты для полученных
    // элементов
    override fun onBindViewHolder(viewholder: ShopItemViewHolder, position: Int) {
        Log.d("ShopListAdapter", " onBindViewHolder, count:${++count}")
        // получение элемента по его позиции
        //val shopItem = shopList[position]
        val shopItem = getItem(position)
        val status = if (shopItem.enabled) {
            "Active"
        }else {
            "Not Active"
        }
        viewholder.view.setOnLongClickListener{
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        viewholder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
           Log.d("ShopListAdapter","setOnClickListener")

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
  // Второй способ: переопределить метод onViewRecycled и устарновить значения по умолчанию
  //  override fun onViewRecycled(viewHolder: ShopItemViewHolder) {
  //     super.onViewRecycled(viewHolder)
  //     viewHolder.tvName.text = ""
  //    viewHolder.tvCount.text = ""
  //    viewHolder.tvName.setTextColor(
    //    ContextCompat.getColor(
    //      viewHolder.view.context,
    //      android.R.color.white))
  // }

    override fun getItemViewType(position: Int): Int {
        // получаем элемент по позиции
        // val item = shopList[position]
       val item = getItem(position)
        // определяем его тип и сразу возвращаем его
        return  if (item.enabled){
            VIEW_TYPE_ENABLED
    }else{
            VIEW_TYPE_DISABLED
        }
    }
   //  при использовании ListAdapter нам не надо переписывать getItemCount()
   //  override fun getItemCount(): Int {
   //     return  shopList.size
   // }



   // interface OnShopItemLongClickListener{
   //     fun onShopItemLongClick(shopItem: ShopItem)
   //     fun onShopClick(shopItem: ShopItem)
   // }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val MAX_POOL_SIZE = 15

    }

}