package com.example.wallet2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AssetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AssetFragment(val position: Int) : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var balance: TextView
    private lateinit var send_button: Button
    private lateinit var receive_button: Button
    private lateinit var recyclerContacts: RecyclerView
    private lateinit var mAdapter : RecyclerAdapter_AssetActivity

    private lateinit var asset: Asset
    private lateinit var activities: MutableList<Asset_Activity>

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        asset = getAsset(position, getContacts())
        activities = filterActivities(assets, asset.symbol)



        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_asset, container, false)

        //toolbar
        //(activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))

        //Setting up buttons
        balance = view.findViewById(R.id.balance_assetFragment)
        send_button = view.findViewById(R.id.send_btn_assetFrg)
        receive_button = view.findViewById(R.id.receive_btn_assetFrg)
        recyclerContacts = view.findViewById(R.id.recyclerContacts_assetFrg)
        toolbar = view.findViewById(R.id.app_bar)

        toolbar.setNavigationOnClickListener {
            val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit() }

        recyclerContacts.setHasFixedSize(true)
        //nuestro layout va a ser de una sola columna
        recyclerContacts.layoutManager = LinearLayoutManager(context)
        //seteando el Adapter
        mAdapter = RecyclerAdapter_AssetActivity( context,activities)
        //asignando el Adapter al RecyclerView
        recyclerContacts.adapter = mAdapter

        balance.text = asset.balance.toString() + " "+ asset.symbol
        // Inflate the layout for this fragment
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.astr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AssetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, position: Int) =
            AssetFragment(position).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getAsset(position: Int, assets: MutableList<Asset>): Asset{
        var asset = assets[position]
        return asset
    }

    private fun filterActivities(list: List<Asset_Activity>, currency: String): MutableList<Asset_Activity>
    {
        var activities:MutableList<Asset_Activity> = ArrayList()
        list.forEach {
            if (it.currency == currency)
                activities.add(it)
        }
        return activities
    }

    //generamos datos dummy con este método
    private fun getContacts(): MutableList<Asset>{
        var assets:MutableList<Asset> = ArrayList()

        assets.add(Asset("Bitcoin", 0.5, 38000.0,19000.0,R.drawable.astr_btc_symbol, "BTC"))
        assets.add(Asset("Ether", 3.0, 2000.0,6000.0,R.drawable.astr_eth_symbol, "ETH"))
        assets.add(Asset("Cardano", 500.0, 1.33,665.0,R.drawable.astr_cardano_symbol,"ADA"))
        assets.add(Asset("Tether", 50.0, 1.0,50.0,R.drawable.astr_tether_symbol, "USDT"))
        assets.add(Asset("Binance", 7.0, 300.0,2100.0,R.drawable.astr_bnb_symbol,"BNB"))
        assets.add(Asset("xDai", 17.0, 1.0,17.0,R.drawable.astr_xdai_symbol, "xDai"))
        assets.add(Asset("ChainLink", 35.0, 18.35,642.25,R.drawable.astr_chainlink_symbol,"Link"))
        assets.add(Asset("AXS", 40.5, 38.48,1558.44,R.drawable.astr_axs_symbol, "AXS"))

        return assets
    }

    var assets  = mutableListOf(
        Asset_Activity("Receive", 0.5, "Juan","June 25",R.drawable.astr_receive_icon, "BNB"),
        Asset_Activity("Transfer", 1.5, "Gustavo","June 25",R.drawable.astr_send_icon, "BTC"),
        Asset_Activity("Transfer", 0.5, "Carlos","June 25",R.drawable.astr_send_icon,"BTC"),
        Asset_Activity("Transfer", 1.5, "David","June 24",R.drawable.astr_send_icon, "BNB"),
        Asset_Activity("Receive", 0.5, "Victor","June 24",R.drawable.astr_receive_icon," ETH"),
        Asset_Activity("Receive", 3.6, "Andrea","June 23",R.drawable.astr_receive_icon, "BTC"),
        Asset_Activity("Transfer", 2.5, "David","June 22",R.drawable.astr_send_icon,"ETH"),
        Asset_Activity("Receive", 0.3, "David","June 22",R.drawable.astr_receive_icon, "BNB")
    )
}