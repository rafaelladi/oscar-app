package com.dietrich.myapplication

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    val mListener: OnItemClickListener
) : RecyclerView.OnItemTouchListener {
    val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if(child != null) {
                    mListener.onItemClick(child, recyclerView.getChildAdapterPosition(child))
                }
                return true
            }
        })
    }


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val chieldView = rv.findChildViewUnder(e.x, e.y)
        return chieldView != null && gestureDetector.onTouchEvent(e)
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}

interface OnItemClickListener : AdapterView.OnItemClickListener {
    fun onItemClick(view: View, position: Int)
    fun onLongItemClick(view: View, position: Int)
}