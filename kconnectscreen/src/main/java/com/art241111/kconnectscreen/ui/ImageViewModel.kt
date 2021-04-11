package com.art241111.kconnectscreen.ui

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.art241111.kconnectscreen.utils.ImagesFromAssets

class ImageViewModel : ViewModel() {
    private val imagesFromAssets = ImagesFromAssets()
    val promoImage: LiveData<ImageBitmap> = imagesFromAssets.getImage()

    fun startChangeImage(context: Context) {
        imagesFromAssets.start(context)
    }

    fun stopChangeImage() {
        imagesFromAssets.stop()
    }

    override fun onCleared() {
        stopChangeImage()
        super.onCleared()
    }
}
