package com.mainway.store.feature.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mainway.store.R
import com.mainway.store.common.EXTRA_KEY_DATA
import com.mainway.store.common.NikeActivity
import com.mainway.store.data.Product
import com.mainway.store.feature.commen.ProductListAdapter
import com.mainway.store.feature.commen.VIEW_TYPE_LARGE
import com.mainway.store.feature.commen.VIEW_TYPE_SMALL
import com.mainway.store.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity(), ProductListAdapter.ProductEventListener {

    private val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }
    private val viewModel: ProductsListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        val gridLayoutManager = GridLayoutManager(this, 2)
        productsRv.layoutManager = gridLayoutManager
        productsRv.adapter = productListAdapter
        viewModel.productLiveData.observe(this) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        viewModel.selectedSortTitleLiveData.observe(this) {
            selectedSortTitleTv.text = getString(it)
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        toolbarView.onBackClickListener = View.OnClickListener {
            finish()
        }

        productListAdapter.productEventListener = this

        viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                viewTypeChangerBtn.setImageResource(R.drawable.ic_viewtype_large)
                productListAdapter.notifyDataSetChanged()
            } else {
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.notifyDataSetChanged()
            }
        }

        sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortTitleArray, viewModel.sort
                ) { dialog, sortSelectedSortIndex ->
                    dialog.dismiss()
                    viewModel.onSelectedSortChangeByUser(sortSelectedSortIndex)

                }.setTitle(getString(R.string.sort))
            dialog.show()
        }

    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onFavoriteBtnClick(product: Product) {

    }
}