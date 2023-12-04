package com.mbs.workoutplan.util

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mbs.workoutplan.R

//fun ImageView.load(
//    imageUrl: String,
//    transform: Transformation? = null,
//    imgScale: Scale? = Scale.FILL,
//    crossfade: Int = 100
//) {
//    this.load(imageUrl) {
//        val loadingDrawable = CircularProgressDrawable(context).apply {
//            strokeWidth = 5f
//            centerRadius = 30f
//            setColorSchemeColors(ContextCompat.getColor(context, R.color.primary))
//            start()
//        }
//        crossfade(crossfade)
//        placeholder(loadingDrawable)
//        transform?.let { transformations(it) }
//        imgScale?.let { scale(it) }
//    }
//}

//fun ImageView.load(
//    @DrawableRes imageResource: Int,
//    transform: Transformation? = null,
//    imgScale: Scale? = Scale.FILL,
//    crossfade: Int = 100
//) {
//    this.load(imageResource) {
//        val loadingDrawable = CircularProgressDrawable(context).apply {
//            strokeWidth = 6f
//            centerRadius = 35f
//            setColorSchemeColors(ContextCompat.getColor(context, R.color.primary))
//            start()
//        }
//        crossfade(crossfade)
//        placeholder(loadingDrawable)
//        transform?.let { transformations(it) }
//        imgScale?.let { scale(it) }
//    }
//}

fun ImageView.load(
    imageUrl: String
) {
    val loadingDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        setColorSchemeColors(ContextCompat.getColor(context, R.color.primary))
        start()
    }
    val requestOptions = RequestOptions()
        .placeholder(loadingDrawable)
        .optionalCenterCrop()

    Glide.with(context)
        .load(imageUrl)
        .apply(requestOptions)
        .into(this)
}