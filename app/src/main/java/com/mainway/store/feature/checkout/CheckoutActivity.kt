package com.mainway.store.feature.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mainway.store.R
import com.mainway.store.common.EXTRA_KEY_ID
import com.mainway.store.common.formatPrice
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.item_purchase_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckoutActivity : AppCompatActivity() {

    val viewModel: CheckoutViewModel by viewModel {
        val uri: Uri? = intent.data
        if (uri != null) {
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        } else {
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        viewModel.checkoutLiveData.observe(this) {
            orderPriceTv.text = formatPrice(it.payable_price)
            orderStatusTv.text = it.payment_status
            purchaseStatusTv.text =
                if (it.purchase_success) "خرید با موفقیت انجام شد " else "خرید ناموفق"
        }
    }
}