package com.example.wallet2

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var send_button:Button
    private lateinit var receive_button:Button
    private lateinit var asset_button:Button
    private lateinit var activity_button:Button

    private lateinit var recyclerContacts: RecyclerView
    private lateinit var mAdapter : RecyclerAdapter
    private lateinit var mAdapter_activity: RecyclerAdapter_AssetActivity


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //MDC-102
        setHasOptionsMenu(true)

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
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        //toolbar
        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))

        //Setting up buttons
        send_button = view.findViewById(R.id.send)
        receive_button = view.findViewById(R.id.receive)
        asset_button = view.findViewById(R.id.button3)
        activity_button = view.findViewById(R.id.button4)

        recyclerContacts = view.findViewById(R.id.recyclerContacts)

        setUpRecyclerView()

        send_button.setOnClickListener {
            val sendFragment = SendFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, sendFragment)
            transaction.commit()
        }


        receive_button.setOnClickListener {
            val receiveFragment = ReceiveFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, receiveFragment)
            transaction.commit()
        }

        activity_button.setOnClickListener {
            setUpRecyclerView_Activity()
        }

        asset_button.setOnClickListener {
            setUpRecyclerView()
        }



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
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView(){
        recyclerContacts.setHasFixedSize(true)
        //nuestro layout va a ser de una sola columna
        recyclerContacts.layoutManager = LinearLayoutManager(context)
        //seteando el Adapter
        mAdapter = RecyclerAdapter(context,getContacts())
        //asignando el Adapter al RecyclerView
        recyclerContacts.adapter = mAdapter
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView_Activity(){
        recyclerContacts.setHasFixedSize(true)
        //nuestro layout va a ser de una sola columna
        recyclerContacts.layoutManager = LinearLayoutManager(context)
        //seteando el Adapter
        mAdapter_activity = RecyclerAdapter_AssetActivity( context,getAssets_Activities())
        //asignando el Adapter al RecyclerView
        recyclerContacts.adapter = mAdapter_activity
    }

    //generamos datos dummy con este método
    private fun getContacts(): MutableList<Asset>{
        var assets:MutableList<Asset> = ArrayList()

        assets.add(Asset("Bitcoin", 0.5, 38000.0,19000.0,R.drawable.astr_btc_symbol))
        assets.add(Asset("Ether", 3.0, 2000.0,6000.0,R.drawable.astr_eth_symbol))
        assets.add(Asset("Cardano", 500.0, 1.33,665.0,R.drawable.astr_cardano_symbol))
        assets.add(Asset("Tether", 50.0, 1.0,50.0,R.drawable.astr_tether_symbol))
        assets.add(Asset("Binance", 7.0, 300.0,2100.0,R.drawable.astr_bnb_symbol))
        assets.add(Asset("xDai", 17.0, 1.0,17.0,R.drawable.astr_xdai_symbol))
        assets.add(Asset("ChainLink", 35.0, 18.35,642.25,R.drawable.astr_chainlink_symbol))
        assets.add(Asset("AXS", 40.5, 38.48,1558.44,R.drawable.astr_axs_symbol))

        return assets
    }

    //generamos datos dummy con este método
    private fun getAssets_Activities(): MutableList<Asset_Activity>{
        var assets:MutableList<Asset_Activity> = ArrayList()

        assets.add(Asset_Activity("Receive", 0.5, "Juan","June 25",R.drawable.astr_receive_icon, "BNB"))
        assets.add(Asset_Activity("Transfer", 1.5, "Gustavo","June 25",R.drawable.astr_send_icon, "BTC"))
        assets.add(Asset_Activity("Transfer", 0.5, "Carlos","June 25",R.drawable.astr_send_icon,"BTC"))
        assets.add(Asset_Activity("Transfer", 1.5, "David","June 24",R.drawable.astr_send_icon, "BNB"))
        assets.add(Asset_Activity("Receive", 0.5, "Victor","June 24",R.drawable.astr_receive_icon," ETH"))
        assets.add(Asset_Activity("Receive", 3.6, "Andrea","June 23",R.drawable.astr_receive_icon, "BTC"))
        assets.add(Asset_Activity("Transfer", 2.5, "David","June 22",R.drawable.astr_send_icon,"ETH"))
        assets.add(Asset_Activity("Receive", 0.3, "David","June 22",R.drawable.astr_receive_icon, "BNB"))

        return assets
    }
}