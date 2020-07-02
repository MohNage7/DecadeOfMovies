package com.mohnage7.swvl.framework.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun clear()

    open fun bindViews(position: Int) {
        clear()
    }
}
