package com.mainway.store.feature.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mainway.store.R

import com.mainway.store.data.Product
import com.mainway.store.services.ImageLoadingService
import com.mainway.store.view.NikeImageView

class FavoriteProductsAdapter(
    private val products: MutableList<Product>,
    val favoriteProductEventListener: FavoriteProductEventListener,
    val imageLoadingService: ImageLoadingService
) : RecyclerView.Adapter<FavoriteProductsAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTv: TextView = itemView.findViewById(R.id.ItemProductTitleTv)
        private val productIV: NikeImageView = itemView.findViewById(R.id.nikeImageView)
        fun bindProduct(product: Product) {
            titleTv.text = product.title
            imageLoadingService.load(productIV, product.image)
            itemView.setOnClickListener {
                favoriteProductEventListener.onClick(product)
            }

            itemView.setOnLongClickListener {
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener.onLongClick(product)
                return@setOnLongClickListener false
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

    interface FavoriteProductEventListener {
        fun onClick(product: Product)
        fun onLongClick(product: Product)
    }
}