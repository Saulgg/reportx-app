package com.dev.sandia.reportx

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class loginAdapter (ctx: Context, layoutResId: Int,  usersList: List<Users>)
    : ArrayAdapter<Users>(ctx, layoutResId, usersList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getView(position, convertView, parent)
    }
}