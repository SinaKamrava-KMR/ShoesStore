package com.mainway.store.feature.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mainway.store.R
import com.mainway.store.common.*
import com.mainway.store.data.Comment
import com.mainway.store.feature.ProductDetailViewModel
import com.mainway.store.feature.product.comment.CommentsListActivity
import com.mainway.store.services.ImageLoadingService
import com.mainway.store.view.scroll.ObservableScrollViewCallbacks
import com.mainway.store.view.scroll.ScrollState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import java.util.ArrayList

class ProductDetailActivity : NikeActivity() {

    private val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter = CommentAdapter()
    val compositeDisposable=CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        initViews()
        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(productIV, it.image)
            productTitleTV.text = it.title
            previousPrice.text = formatPrice(it.previous_price)
            previousPrice.paintFlags = android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTv.text = formatPrice(it.price)
            toolBarTitleTv.text = it.title
        }

        productDetailViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        productDetailViewModel.commentLiveData.observe(this) {
            Timber.i(it.toString())
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3) {
                viewAllCommentBtn.visibility = View.VISIBLE
                viewAllCommentBtn.setOnClickListener {
                    startActivity(Intent(this, CommentsListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id)
                    })
                }
            } else {
                viewAllCommentBtn.visibility = View.GONE
            }

        }


    }

    private fun initViews() {
        commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentsRv.adapter = commentAdapter
        commentsRv.isNestedScrollingEnabled = false

        productIV.post {

            val productIvHeight = productIV.height
            val toolbar = toolbarView
            val productImageView = productIV

            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {

                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })


        }


        addToCartBtn.setOnClickListener {
            productDetailViewModel.onAddToCartBtn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :NikeCompletableObserve(compositeDisposable){
                    override fun onComplete() {
                        showSnackBar(getString(R.string.success_addToCart))
                    }

                })
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}