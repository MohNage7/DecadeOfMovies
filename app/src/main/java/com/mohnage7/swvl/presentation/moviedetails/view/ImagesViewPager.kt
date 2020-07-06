package com.mohnage7.swvl.presentation.moviedetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohnage7.swvl.R
import com.mohnage7.swvl.framework.base.BaseViewHolder
import com.mohnage7.swvl.presentation.moviedetails.view.ImagesViewPager.ViewHolder
import kotlinx.android.synthetic.main.item_image.view.*

class ImagesViewPager(private val imagesList: List<String>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bindViews(position)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    inner class ViewHolder(itemView: View) :
        BaseViewHolder(itemView) {

        override fun bindViews(position: Int) {
            super.bindViews(position)
            val image = imagesList[position]
            loadImage(image)
        }

        private fun loadImage(image: String) {
            Glide.with(itemView.context)
                .load(image)
                .placeholder(R.color.colorPrimaryDark)
                .error(R.drawable.ic_broken_image)
                .into(itemView.movieImgView)
        }

        override fun clear() {
            itemView.movieImgView.setImageDrawable(null)
        }
    }
}