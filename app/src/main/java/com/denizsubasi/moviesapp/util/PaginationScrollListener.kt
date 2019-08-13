package com.denizsubasi.moviesapp.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(internal var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract var isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        // change this to customize the loading offset
        val threshold = 10 // from the last x item it will load the new data from backend

        if (!isLoading) {
            if (visibleItemCount + firstVisibleItemPosition >= (totalItemCount - threshold) && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
}
