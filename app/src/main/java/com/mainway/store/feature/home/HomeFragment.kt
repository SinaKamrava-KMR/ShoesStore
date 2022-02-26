package com.mainway.store.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainway.store.R
import com.mainway.store.common.EXTRA_KEY_DATA
import com.mainway.store.common.NikeFragment
import com.mainway.store.common.convertDpToPixel
import com.mainway.store.data.Product
import com.mainway.store.data.SORT_LATEST
import com.mainway.store.feature.commen.ProductListAdapter
import com.mainway.store.feature.commen.VIEW_TYPE_ROUND
import com.mainway.store.feature.list.ProductListActivity
import com.mainway.store.feature.main.BannerSliderAdapter
import com.mainway.store.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class HomeFragment : NikeFragment(), ProductListAdapter.ProductEventListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }
    private val productPopularAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latestProducts.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        productListAdapter.productEventListener = this
        latestProducts.adapter = productListAdapter

        popularProducts.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        productPopularAdapter.productEventListener = this
        popularProducts.adapter = productPopularAdapter



        homeViewModel.productLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        homeViewModel.productPopularLiveData.observe(viewLifecycleOwner) {
            productPopularAdapter.products = it as ArrayList<Product>
        }

        viewLatestProductBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }


        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter
            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
                32f,
                requireContext()
            )) * 173) / 328).toInt()

            val layoutParams = bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParams
            sliderIndicator.setViewPager2(bannerSliderViewPager)
        }


    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })


    }

    override fun onFavoriteBtnClick(product: Product) {
        homeViewModel.addToProductFavorite(product)
    }


}