package com.mainway.store.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mainway.store.R
import com.mainway.store.common.EXTRA_KEY_DATA
import com.mainway.store.data.Banner
import com.mainway.store.services.ImageLoadingService
import com.mainway.store.view.NikeImageView
import org.koin.android.ext.android.inject
import java.lang.IllegalStateException

class BannerFragment : Fragment() {

    val imageLoadingService: ImageLoadingService by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView =
            inflater.inflate(R.layout.fragment_banner, container, false) as NikeImageView
        val banner= requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)?: throw IllegalStateException("banner can not be null")
        imageLoadingService.load(imageView,banner.image)
        return imageView
    }

    companion object {
        fun newInstance(banner: Banner) : BannerFragment {
          return  BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA, banner)
                }
            }

        }
    }

}