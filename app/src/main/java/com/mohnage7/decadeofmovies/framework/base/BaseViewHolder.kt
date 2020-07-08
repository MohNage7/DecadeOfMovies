package com.mohnage7.decadeofmovies.framework.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun clear()

    open fun bindViews(position: Int) {
        clear()
    }
}
