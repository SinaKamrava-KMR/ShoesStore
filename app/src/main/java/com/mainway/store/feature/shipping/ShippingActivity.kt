package com.mainway.store.feature.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mainway.store.R
import com.mainway.store.common.EXTRA_KEY_DATA
import com.mainway.store.common.EXTRA_KEY_ID
import com.mainway.store.common.NikeSingleObserver
import com.mainway.store.common.openUrlInCustomTab
import com.mainway.store.data.PurchaseDetail
import com.mainway.store.data.SubmitOrderResult
import com.mainway.store.feature.cart.CartItemAdapter
import com.mainway.store.feature.checkout.CheckoutActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException

class ShippingActivity : AppCompatActivity() {

    val viewModel: ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?: throw  IllegalStateException("purchase Detail can not be null")

        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
        viewHolder.bind(
            purchaseDetail.total_price,
            purchaseDetail.shipping_cost,
            purchaseDetail.payable_price
        )


        val onClickListener = View.OnClickListener {
            viewModel.submitOrder(
                firstNameEt.text.toString(),
                lastNameEt.text.toString(),
                postalCodeNameEt.text.toString(),
                phoneNumberEt.text.toString(),
                addressEt.text.toString(),
                if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable) {
                    override fun onSuccess(t: SubmitOrderResult) {
                        if (t.bank_gateway_url.isNotEmpty()) {
                            openUrlInCustomTab(this@ShippingActivity, t.bank_gateway_url)
                        } else {
                            startActivity(
                                Intent(
                                    this@ShippingActivity,
                                    CheckoutActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_KEY_ID, t.order_id)
                                })
                        }
                        finish()
                    }

                })
        }

        onlinePaymentBtn.setOnClickListener(onClickListener)
        codBtn.setOnClickListener(onClickListener)
    }
}