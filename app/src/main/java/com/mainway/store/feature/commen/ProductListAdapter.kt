package com.mainway.store.feature.commen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mainway.store.R
import com.mainway.store.common.formatPrice
import com.mainway.store.common.implementSpringAnimationTrait
import com.mainway.store.data.Product
import com.mainway.store.services.ImageLoadingService
import com.mainway.store.view.NikeImageView
import java.lang.IllegalStateException

const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_LARGE = 2

class ProductListAdapter(
    var viewType: Int = VIEW_TYPE_ROUND,
    val imageLoadingService: ImageLoadingService
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var onProductClickListener: OnProductClickListener? = null

    var products = ArrayList<Product>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        private val productIV: NikeImageView = itemView.findViewById(R.id.productIV)
        private val currentPrice: TextView = itemView.findViewById(R.id.currentPriceTV)
        private val previousPrice: TextView = itemView.findViewById(R.id.previousPriceTv)

        fun bindProduct(product: Product) {
            imageLoadingService.load(productIV, product.image)
            titleTv.text = product.title
            currentPrice.text = formatPrice(product.price)
            previousPrice.text = formatPrice(product.previous_price)
            previousPrice.paintFlags = android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                onProductClickListener?.onProductClick(product)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutResId=when(viewType){
            VIEW_TYPE_ROUND->R.layout.item_product
            VIEW_TYPE_SMALL->R.layout.item_product_small
            VIEW_TYPE_LARGE->R.layout.item_product_large
            else -> throw IllegalStateException("viewType is not valid")
        }
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size


    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }
}