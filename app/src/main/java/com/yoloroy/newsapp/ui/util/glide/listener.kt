package com.yoloroy.newsapp.ui.util.glide

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun RequestBuilder<Drawable>.addListener(
    onReady: (
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ) -> Boolean = { _, _, _, _, _ -> false },
    onFail: (
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ) -> Boolean = { _, _, _, _ -> false }
) = addListener(RequestListenerOverrideInArgsImpl(onReady, onFail))

private class RequestListenerOverrideInArgsImpl(
    private val _onResourceReady: (resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean) -> Boolean,
    private val _onLoadFailed: (e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean) -> Boolean
) : RequestListener<Drawable> {

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ) = _onResourceReady(resource, model, target, dataSource, isFirstResource)

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ) = _onLoadFailed(e, model, target, isFirstResource)
}
