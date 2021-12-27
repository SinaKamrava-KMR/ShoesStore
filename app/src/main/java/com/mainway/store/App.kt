package com.mainway.store

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.mainway.store.data.repo.*
import com.mainway.store.data.repo.source.*
import com.mainway.store.feature.ProductDetailViewModel
import com.mainway.store.feature.auth.AuthViewModel
import com.mainway.store.feature.commen.ProductListAdapter
import com.mainway.store.feature.list.ProductsListViewModel
import com.mainway.store.feature.home.HomeViewModel
import com.mainway.store.feature.product.comment.CommentListViewModel
import com.mainway.store.services.FrescoImageLoadingService
import com.mainway.store.services.ImageLoadingService
import com.mainway.store.services.http.ApiService
import com.mainway.store.services.http.createApiServiceInstance
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }

            factory { (viewType:Int)-> ProductListAdapter(viewType,get()) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            single<SharedPreferences> { this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
            single<UserRepository> { UserRepositoryImpl(UserLocalDataSource(get()),UserRemoteDataSource(get())) }
            single<UserDataSource> { UserLocalDataSource(get()) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(),get()) }
            viewModel { (productID: Int) -> CommentListViewModel(productID, get()) }
            viewModel { (sort: Int) -> ProductsListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }


        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository :UserRepository = get()
        userRepository.loadToken()

    }
}