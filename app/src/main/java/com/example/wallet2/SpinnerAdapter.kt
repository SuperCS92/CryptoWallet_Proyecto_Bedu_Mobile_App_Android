package com.example.wallet2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class SpinnerAdapter(context: Context,
                     val list: List<String>,
                     val list_2: List<Asset_Short>): ArrayAdapter<String>(context,R.layout.asset_item_short,list ) {


    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    public fun getCustomView(position:Int, view: View?, parent: ViewGroup): View {
        val inflater =  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var row = inflater.inflate(R.layout.asset_item_short,parent,false)

        var asset_name = row.findViewById(R.id.asset_name) as TextView
        var asset_img = row.findViewById(R.id.userImage) as ImageView

        var res = context.resources
        var drawableName = list_2.get(position).idImage.toString().lowercase(Locale.getDefault())
        var resId = res.getIdentifier(drawableName,"drawable",context.packageName)
        var drawable = res.getDrawable(resId)

        asset_name.setText(list_2.get(position).name)
        asset_img.setImageDrawable(drawable)

        return row
    }

}