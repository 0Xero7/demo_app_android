package com.example.traveloptions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PreferenceItemDecoration(private val spacing : Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val pos = parent.getChildAdapterPosition(view)

        outRect.left = spacing
        outRect.right = spacing

        outRect.top = if (pos == 0) spacing else spacing / 2
        outRect.bottom = if (pos == parent.childCount - 1) spacing else spacing / 2
    }
}