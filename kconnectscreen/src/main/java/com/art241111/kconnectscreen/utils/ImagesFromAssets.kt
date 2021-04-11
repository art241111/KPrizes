package com.art241111.kconnectscreen.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val IMAGE_DIRECTION = "promo_images"

class ImagesFromAssets {
    private val _image: MutableLiveData<ImageBitmap> = MutableLiveData<ImageBitmap>()
    fun getImage(): LiveData<ImageBitmap> = _image

    private var isImageUpload = false

    fun start(
        context: Context,
        directName: String = IMAGE_DIRECTION
    ) {
        if (!isImageUpload) {
            isImageUpload = true
            startUpdate(context, directName)
        }
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private fun startUpdate(
        context: Context,
        directName: String = IMAGE_DIRECTION
    ) {
        scope.launch {
            val images = context.assets.list(directName)

            if (images != null) {
                var position = -1

                while (isImageUpload) {
                    position = (++position) % images.size
                    val inputStream = context.assets.open(directName + "/" + images[position])
                    try {
                        val image = Drawable.createFromStream(inputStream, null).toBitmap()
                        _image.postValue(image.asImageBitmap())
                    } catch (e: OutOfMemoryError) {
                        Log.e("ImageFromAssets", e.stackTrace.toString())
                    }

                    delay(2000L)
                }
            }
        }
    }

    fun stop() {
        isImageUpload = false
    }
}
