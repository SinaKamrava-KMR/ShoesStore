package com.mainway.store.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.mainway.store.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onBackClickListener: View.OnClickListener? = null
        set(value) {
            field = value
            backBtn.setOnClickListener(onBackClickListener)
        }

    init {
        inflate(context, R.layout.view_toolbar, this)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.NikeToolbar)
            val title = a.getString(R.styleable.NikeToolbar_nt_title)
            if (title != null && title.isNotEmpty()) {
                toolbarTitleTv.text = title
            }

            a.recycle()
        }
    }
}