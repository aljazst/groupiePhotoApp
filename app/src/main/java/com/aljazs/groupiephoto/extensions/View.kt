package com.aljazs.groupiephoto.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun <T : View> T.extClick(block: (T) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    setOnClickListener { view -> block(view as T) }
}

fun <T : View> T.extClickOnce(block: (T) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    setOnClickListener { view ->
        view.isEnabled = false
        view.postDelayed(Runnable { view.isEnabled = true }, 500)
        block(view as T)
    }
}

fun View.extVisible() {
    this.visibility = View.VISIBLE
}

fun View.extGone() {
    this.visibility = View.GONE
}

fun View.extInvisible() {
    this.visibility = View.INVISIBLE
}

fun ViewGroup.extInflate(
    @LayoutRes layoutResource: Int,
    attachToParentRoot: Boolean = false
): View {
    val layoutInflater = LayoutInflater.from(context)
    return layoutInflater.inflate(layoutResource, this, attachToParentRoot)
}

fun Int.createBitmap(context: Context): Bitmap {
    return BitmapFactory.decodeResource(context.resources, this)
}