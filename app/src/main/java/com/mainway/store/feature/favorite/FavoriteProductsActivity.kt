package com.mainway.store.feature.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mainway.store.R
import com.mainway.store.common.EXTRA_KEY_DATA
import com.mainway.store.common.NikeActivity
import com.mainway.store.data.Product
import com.mainway.store.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_favorite_products.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteProductsActivity : NikeActivity(),FavoriteProductsAdapter.FavoriteProductEventListener {
    val viewModel:FavoriteProductViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_products)

        helpBtn.setOnClickListener {
            Snackbar.make(it,R.string.favorite_help_message,Snackbar.LENGTH_LONG).show()
        }
        viewModel.productsLiveData.observe(this){
            if(it.isNotEmpty()){


            favoriteProductRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

            favoriteProductRv.adapter=FavoriteProductsAdapter(it as MutableList<Product>,this,get())
            }else{
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text= getString(R.string.favoriteEmptyStateMessage)
            }
        }



    }

    override fun onClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }

    override fun onLongClick(product: Product) {
       viewModel.removerFroFavorite(product)
    }
}