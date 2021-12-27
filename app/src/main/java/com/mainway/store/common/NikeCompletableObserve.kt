package com.mainway.store.common

import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

abstract class NikeCompletableObserve(private val compositeDisposable: CompositeDisposable) :
    CompletableObserver {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.addAll(d)
    }

    override fun onError(e: Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(e))
        Timber.e(e.toString())
    }

}