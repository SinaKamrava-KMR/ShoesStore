package com.mainway.store.feature.cart

import androidx.lifecycle.MutableLiveData
import com.mainway.store.R
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.NikeViewModel
import com.mainway.store.common.asyncNetworkRequest
import com.mainway.store.data.*
import com.mainway.store.data.repo.CartRepository
import io.reactivex.Completable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class CartViewModel(private val cartRepository: CartRepository) : NikeViewModel() {
    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    private fun getCartItem() {

        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            emptyStateLiveData.value = EmptyState(false)
            cartRepository.get()
                .asyncNetworkRequest()
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.total_price, t.shipping_cost, t.payable_price)
                        } else {
                            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyState)
                        }
                    }

                })
        } else {
            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyStateLoginRequired, true)
        }
    }

    fun removeItemFromCart(cartItem: CartItem): Completable {
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()

                val cartItemCount= EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count-=cartItem.count
                    EventBus.getDefault().postSticky(it)
                }



                cartItemsLiveData.value?.let {
                    Timber.i("cart Items after remove -> ${it.size}")
                    if (it.isEmpty()) {
                        emptyStateLiveData.postValue(EmptyState(true, R.string.cartEmptyState))
                    }
                }
            }
            .ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count += 1
                    EventBus.getDefault().postSticky(it)
                }

            }
            .ignoreElement()

    fun decreaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess {
                calculateAndPublishPurchaseDetail()
                val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                cartItemCount?.let {
                    it.count -= 1
                    EventBus.getDefault().postSticky(it)
                }
            }
            .ignoreElement()

    fun refresh() {
        getCartItem()
    }

    private fun calculateAndPublishPurchaseDetail() {
        cartItemsLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }
                purchaseDetail.total_price = totalPrice
                purchaseDetail.payable_price = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)

            }
        }
    }
}