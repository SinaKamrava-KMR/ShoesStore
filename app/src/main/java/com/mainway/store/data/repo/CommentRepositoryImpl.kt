package com.mainway.store.data.repo

import com.mainway.store.data.Comment
import com.mainway.store.data.repo.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource) :CommentRepository{

    override fun getAll(product_id:Int): Single<List<Comment>> = commentDataSource.getAll(product_id)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }

}