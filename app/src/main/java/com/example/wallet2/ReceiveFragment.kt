package com.example.wallet2

import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReceiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceiveFragment : Fragment() {

    val items= listOf("BTC", "ETH", "BNB")

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val view = inflater.inflate(R.layout.fragment_receive, container, false)

        val textInputLayout = view.findViewById<TextInputLayout>(R.id.asset_transaction)
        val assetText = view.findViewById<AutoCompleteTextView>(R.id.assetText_transaction)

        val adapter = SpinnerAdapter(requireContext(), items,getAssets_Short())
        (textInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //toolbar
        //(activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))
        toolbar = view.findViewById(R.id.app_bar)
        toolbar.setNavigationOnClickListener {
            val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit() }

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
         * @return A new instance of fragment ReceiveFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReceiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //generamos datos dummy con este método
    private fun getAssets_Short(): MutableList<Asset_Short>{
        var assets:MutableList<Asset_Short> = ArrayList()

        assets.add(Asset_Short("BTC",R.drawable.astr_btc_symbol))
        assets.add(Asset_Short("ETH", R.drawable.astr_eth_symbol))
        assets.add(Asset_Short("BNB",R.drawable.astr_bnb_symbol))

        return assets
    }
}