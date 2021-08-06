package com.example.wallet2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter_AssetActivity(
    var context: Context?,
    var assets: MutableList<Asset_Activity>): RecyclerView.Adapter<RecyclerAdapter_AssetActivity.ViewHolder>() {


    //Aquí atamos el ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = assets.get(position)
        holder.bind(item, context!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_activity, parent, false))
    }

    override fun getItemCount(): Int {
        return assets.size
    }

    //El ViewHolder ata los datos del RecyclerView a la Vista para desplegar la información
    //También se encarga de gestionar los eventos de la View, como los clickListeners
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //obteniendo las referencias a las Views
        val type = view.findViewById(R.id.asset_name) as TextView
        val amount = view.findViewById(R.id.asset_balance) as TextView
        val from_to = view.findViewById(R.id.price_fiat) as TextView
        val date = view.findViewById(R.id.fiat_balance) as TextView
        val image = view.findViewById(R.id.userImage) as ImageView

        //"atando" los datos a las Views
        fun bind(asset: Asset_Activity, context: Context){
            type.text = asset.type
            amount.text = asset.amount.toString()+ " " + asset.currency
            date.text = asset.date
            image.setImageResource(asset.idImage)

            when(type.text){
                "Receive"-> from_to.text = "from: "+ asset.from_to
                "Transfer" -> from_to.text = "to: "+ asset.from_to
            }

            //Gestionando los eventos e interacciones con la vista
            itemView.setOnClickListener{
                Toast.makeText(context, "Abriendo activity con detalles", Toast.LENGTH_SHORT).show()
            }
        }
    }


}