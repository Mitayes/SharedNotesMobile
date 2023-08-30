package com.mitayes.sharednotes.presentation.mainActivity

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mitayes.sharednotes.doIf
import com.mitayes.sharednotes.toDp

class PaddingDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        parent.adapter?.let{
            doIf(parent.getChildAdapterPosition(view) != it.itemCount - 1){
                outRect.bottom = 10.toDp()
            }
        }
    }
}