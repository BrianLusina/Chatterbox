package com.chatterbox.chatterbox.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @author lusinabrian on 02/06/17.
 * @Notes ViewHolders will inherit from this class
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mCurrentPosition: Int = 0

    protected abstract fun clear()

    open fun onBind(position: Int) {
        mCurrentPosition = position
        clear()
    }

    fun getCurrentPosition(): Int {
        return mCurrentPosition
    }
}