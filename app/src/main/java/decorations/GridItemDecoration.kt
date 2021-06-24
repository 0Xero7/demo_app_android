package decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val space : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val pos = parent.getChildAdapterPosition(view)
        val column = pos % 2
        val row = pos / 2

        outRect.left = if (column == 0) space else (space / 2)
        outRect.right = if (column == 1) space else (space / 2)

        outRect.bottom = space
        outRect.top = (if (row == 0) space else 0)
    }

}