//package com.example.wallet2
//
//import android.app.Dialog
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.navigation.Navigation
//import androidx.recyclerview.widget.RecyclerView
//
//class RecyclerAdapter_AssetActivity(
//    var context: Context?,
//    var assets: MutableList<Asset_Activity>): RecyclerView.Adapter<RecyclerAdapter_AssetActivity.ViewHolder>() {
//
//
//    //Aquí atamos el ViewHolder
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = assets.get(position)
//        holder.bind(item, context!!)
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        return ViewHolder(layoutInflater.inflate(R.layout.item_activity, parent, false))
//    }
//
//    override fun getItemCount(): Int {
//        return assets.size
//    }
//
//
//    //El ViewHolder ata los datos del RecyclerView a la Vista para desplegar la información
//    //También se encarga de gestionar los eventos de la View, como los clickListeners
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        //obteniendo las referencias a las Views
//        val type = view.findViewById(R.id.asset_name) as TextView
//        val amount = view.findViewById(R.id.asset_balance) as TextView
//        val from_to = view.findViewById(R.id.price_fiat) as TextView
//        val date = view.findViewById(R.id.fiat_balance) as TextView
//        val image = view.findViewById(R.id.userImage) as ImageView
//        private var loadingDialog: Dialog? = null
//
//        private fun hideLoading() {
//            loadingDialog?.let { if(it.isShowing)it.cancel() }
//        }
//
//        private fun showLoading() {
//            hideLoading()
//            loadingDialog = CommonUtils.showLoadingDialog(itemView.context)
//        }
//
//        //"atando" los datos a las Views
//        fun bind(asset: Asset_Activity, context: Context){
//            type.text = asset.type
//            amount.text = asset.amount.toString()+ " " + asset.currency
//            date.text = asset.date
//            image.setImageResource(asset.idImage)
//
//            when(type.text){
//                "Receive"-> from_to.text = "from: "+ asset.from_to
//                "Transfer" -> from_to.text = "to: "+ asset.from_to
//            }
//
//            //Gestionando los eventos e interacciones con la vista
//            itemView.setOnClickListener{
//                val position: Int = adapterPosition
//                showLoading()
//                android.os.Handler().postDelayed({hideLoading()},3000)
//
///*
//                val transactionFragment = TransactionFragment(position)
//
//                val fragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
//                val transaction = fragmentManager.beginTransaction()
//                transaction.replace(R.id.fragment_container, transactionFragment)
//                transaction.commit()*/
//                if(Navigation.findNavController(it).currentDestination?.id == R.id.dashboardFragmentDest) {
//                    Navigation.findNavController(it)
//                        .navigate(R.id.action_dashboardFragmentDest_to_transactionFragment, null)
//                } else if(Navigation.findNavController(it).currentDestination?.id == R.id.assetFragment){
//                    Navigation.findNavController(it)
//                        .navigate(R.id.action_assetFragment_to_transactionFragment, null)
//                }
//
//            }
//        }
//
//
//    }
//
//
//}



package com.example.wallet2

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.wallet2.data.models.CryptoTransfer
import com.example.wallet2.databinding.FragmentSendBinding
import com.example.wallet2.databinding.ItemActivityBinding
import com.example.wallet2.ui.asset.AssetViewModel

class RecyclerAdapter_AssetActivity(
    private val viewModel: AssetViewModel
    ) :
    ListAdapter<CryptoTransfer, RecyclerAdapter_AssetActivity.ViewHolder>(CryptoTransferDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("onCreateViewHolder", "Recycler")
        return ViewHolder.from(parent)
    }

    //Aquí atamos el ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Hey aqui empieza", "mensage onBindViewHolder")
        val item = getItem(position)
        holder.bind(viewModel, item)
    }

    //El ViewHolder ata los datos del RecyclerView a la Vista para desplegar la información
    //También se encarga de gestionar los eventos de la View, como los clickListeners
    class ViewHolder private constructor(val binding: ItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //obteniendo las referencias a las Views
//        val type = view.findViewById(R.id.asset_name) as TextView
//        val amount = view.findViewById(R.id.asset_balance) as TextView
//        val from_to = view.findViewById(R.id.price_fiat) as TextView
//        val date = view.findViewById(R.id.fiat_balance) as TextView
//        val image = view.findViewById(R.id.userImage) as ImageView
        private var loadingDialog: Dialog? = null

        private fun hideLoading() {
            loadingDialog?.let { if(it.isShowing)it.cancel() }
        }

        private fun showLoading() {
            hideLoading()
            loadingDialog = CommonUtils.showLoadingDialog(itemView.context)
        }

        //"atando" los datos a las Views
        fun bind(viewModel: AssetViewModel, item: CryptoTransfer){
            Log.d("myitem", item.toString())
            binding.viewModel = viewModel
            binding.cryptotransfer = item
            binding.executePendingBindings()
//            type.text = asset.type
//            amount.text = asset.amount.toString()+ " " + asset.currency
//            date.text = asset.date
//            image.setImageResource(asset.idImage)

            when(item.type){
                "Receive"-> binding.userImage.setImageResource(R.drawable.astr_receive_icon)
                "Transfer" -> binding.userImage.setImageResource(R.drawable.astr_send_icon)
            }

            //Gestionando los eventos e interacciones con la vista
            itemView.setOnClickListener{
                val position: Int = adapterPosition
                showLoading()
                android.os.Handler().postDelayed({hideLoading()},3000)

/*
                val transactionFragment = TransactionFragment(position)

                val fragmentManager = (itemView.context as FragmentActivity).supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, transactionFragment)
                transaction.commit()*/
                if(Navigation.findNavController(it).currentDestination?.id == R.id.dashboardFragmentDest) {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_dashboardFragmentDest_to_transactionFragment, null)
                } else if(Navigation.findNavController(it).currentDestination?.id == R.id.assetFragment){
                    Navigation.findNavController(it)
                        .navigate(R.id.action_assetFragment_to_transactionFragment, null)
                }

            }


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemActivityBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }



    }


}

class CryptoTransferDiffCallback : DiffUtil.ItemCallback<CryptoTransfer>() {
    override fun areItemsTheSame(oldItem: CryptoTransfer, newItem: CryptoTransfer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CryptoTransfer, newItem: CryptoTransfer): Boolean {
        return oldItem == newItem
    }
}
