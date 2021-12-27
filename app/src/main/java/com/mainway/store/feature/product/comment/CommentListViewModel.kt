package com.mainway.store.feature.product.comment

import androidx.lifecycle.MutableLiveData
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.NikeViewModel
import com.mainway.store.common.asyncNetworkRequest
import com.mainway.store.data.Comment
import com.mainway.store.data.repo.CommentRepository


class CommentListViewModel(private val product_id: Int, private val commentRepository: CommentRepository) : NikeViewModel() {
val commentsLivData=MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value = true
        commentRepository.getAll(product_id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object :NikeSingleObserver<List<com.mainway.store.data.Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                   commentsLivData.value = t
                }


            })
    }

}