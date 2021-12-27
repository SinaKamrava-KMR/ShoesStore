package com.mainway.store.feature

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.mainway.store.common.EXTRA_KEY_DATA
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.NikeViewModel
import com.mainway.store.common.asyncNetworkRequest
import com.mainway.store.data.Comment
import com.mainway.store.data.Product
import com.mainway.store.data.repo.CartRepository
import com.mainway.store.data.repo.CommentRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(
    bundle: Bundle,
    private val commentRepository: CommentRepository,
    val cartRepository: CartRepository
) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentLiveData.value = t
                }

            })

    }

    fun onAddToCartBtn(): Completable =
        cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()


}