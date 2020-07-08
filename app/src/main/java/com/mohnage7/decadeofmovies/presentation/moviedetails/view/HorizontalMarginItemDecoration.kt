package com.mohnage7.decadeofmovies.presentation.moviedetails.view

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class HorizontalMarginItemDecoration internal constructor(
    context: Context,
    @DimenRes margin: Int
) : ItemDecoration() {
    private val marginLeft = 0
    private val mMargin: Int = context.resources.getDimension(margin).toInt()
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = mMargin
        outRect.left = mMargin
    }

}