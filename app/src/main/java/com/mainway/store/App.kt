package com.mainway.store

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.mainway.store.data.db.AppDatabase
import com.mainway.store.data.repo.*
import com.mainway.store.data.repo.order.OrderRemoteDataSource
import com.mainway.store.data.repo.order.OrderRepository
import com.mainway.store.data.repo.order.OrderRepositoryImpl
import com.mainway.store.data.repo.source.*
import com.mainway.store.feature.ProductDetailViewModel
import com.mainway.store.feature.auth.AuthViewModel
import com.mainway.store.feature.cart.CartViewModel
import com.mainway.store.feature.checkout.CheckoutViewModel
import com.mainway.store.feature.commen.ProductListAdapter
import com.mainway.store.feature.favorite.FavoriteProductViewModel
import com.mainway.store.feature.list.ProductsListViewModel
import com.mainway.store.feature.home.HomeViewModel
import com.mainway.store.feature.main.MainViewModel
import com.mainway.store.feature.product.comment.CommentListViewModel
import com.mainway.store.feature.profile.ProfileViewModel
import com.mainway.store.feature.shipping.ShippingViewModel
import com.mainway.store.services.FrescoImageLoadingService
import com.mainway.store.services.ImageLoadingService
import com.mainway.store.services.http.ApiService
import com.mainway.store.services.http.NikeAuthenticator
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
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            single { Room.databaseBuilder(this@App,AppDatabase::class.java,"app.db").build() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    get<AppDatabase>().productDao()
                )
            }

            single<SharedPreferences> {
                this@App.getSharedPreferences(
                    "app_settings",
                    MODE_PRIVATE
                )
            }
            single { UserLocalDataSource(get()) }
            single<UserRepository> {
                UserRepositoryImpl(
                    UserLocalDataSource(get()),
                    UserRemoteDataSource(get())
                )
            }

            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }


            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductsListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId:Int)-> CheckoutViewModel(orderId,get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository: UserRepository = get()
        userRepository.loadToken()
    }
}