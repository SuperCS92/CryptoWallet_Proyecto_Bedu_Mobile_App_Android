package com.example.wallet2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    var context: Context?,
    var assets: MutableList<Asset>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    //Aquí atamos el ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = assets.get(position)
        holder.bind(item, context!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_asset, parent, false))
    }

    override fun getItemCount(): Int {
        return assets.size
    }

    //El ViewHolder ata los datos del RecyclerView a la Vista para desplegar la información
    //También se encarga de gestionar los eventos de la View, como los clickListeners
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //obteniendo las referencias a las Views
        val asset_name = view.findViewById(R.id.asset_name) as TextView
        val price_fiat = view.findViewById(R.id.price_fiat) as TextView
        val asset_balance = view.findViewById(R.id.asset_balance) as TextView
        val fiat_balance = view.findViewById(R.id.fiat_balance) as TextView
        val image = view.findViewById(R.id.userImage) as ImageView

        //"atando" los datos a las Views
        fun bind(asset: Asset, context: Context) {
            asset_name.text = asset.name
            price_fiat.text = "$" + asset.fiat_price.toString()
            asset_balance.text = asset.balance.toString()
            fiat_balance.text = "$" + asset.balance_fiat.toString()
            image.setImageResource(asset.idImage)

            //Gestionando los eventos e interacciones con la vista
            itemView.setOnClickListener {
                Toast.makeText(context, "Llamando a ${asset.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}