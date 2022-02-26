package com.mainway.store.data

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow: Boolean,
    @StringRes val messageResId: Int=0,
    val mustShowCalToActionButton: Boolean=false
)
