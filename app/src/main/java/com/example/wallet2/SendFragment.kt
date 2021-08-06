package com.example.wallet2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SendFragment : Fragment() {


    val items= listOf("BTC", "ETH", "BNB")
    val itemsprice= listOf(39249.40, 2681.89, 330.14)
    private lateinit var amount_textField: TextInputLayout
    private lateinit var amount_value: EditText
    private lateinit var total : TextView
    private lateinit var cance_button: MaterialButton

    //This boolean is used to check wether the amount should be displaced
    //on criptocurrency or in fiat currency {true= crypto / false= fiat}
    var amount_currency = true

    //This boolean is used to track wether as criptocurrency was set before clicking
    //compare endicon in amount text field
    var error = false

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_send, container, false)

        val textInputLayout = view.findViewById<TextInputLayout>(R.id.asset_send)
        val assetText = view.findViewById<AutoCompleteTextView>(R.id.assetText)
        amount_value = view.findViewById(R.id.amount_value)            //--------EdiText
        amount_textField = view.findViewById(R.id.amount_textField)
        total = view.findViewById(R.id.textView11)                     //---------TextView
        cance_button = view.findViewById(R.id.button2)

        
        val adapter = SpinnerAdapter(requireContext(), items,getAssets_Short())
        (textInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)


        amount_value.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode >=7 && keyCode <= 16 && event.action == KeyEvent.ACTION_UP) {
                    val textamount = amount_value.text

                            if(textamount.isNotEmpty()) {
                                val amount = textamount.toString().toDouble()
                                val fee: Double = 0.00034

                                if(amount_currency) {
                                    val _total = amount.plus(fee)
                                    total.text = _total.toString()
                                }
                                else{
                                    var crypto = assetText.text.toString()
                                    var amount_converted : Double = 0.0

                                    //We need to convert it from fiat to cripto
                                    when(crypto){
                                        "BTC" ->  amount_converted =  amount / itemsprice[0]

                                        "ETH" ->   amount_converted =  amount / itemsprice[1]

                                        "BNB" ->   amount_converted =  amount / itemsprice[2]
                                    }

                                    val _total = amount_converted.plus(fee)
                                    total.text = _total.toString()
                                }


                                return@OnKeyListener true
                            }

                    false
                }
                false
        })


        //toolbar
        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))

        //Logic to convert value entered in amount text field from cripto to fiat and viceversa
        amount_textField.setEndIconOnClickListener{
            var crypto = assetText.text.toString()
            var amount = amount_value.text.toString().toDouble()
            var amount_converted = 0.0


                when(amount_currency) {

                true -> when (crypto) {
                    "BTC" -> { amount_converted = itemsprice[0] * amount
                               error = false}
                    "ETH" -> { amount_converted = itemsprice[1] * amount
                               error = false}
                    "BNB" -> { amount_converted = itemsprice[2] * amount
                               error = false}
                    else-> {amount_textField.error = getString(R.string.astr_amount_error)
                            error = true}
                }

                false -> when (crypto) {
                    "BTC" -> { amount_converted =  amount / itemsprice[0]
                                error = false}
                    "ETH" ->  { amount_converted =  amount / itemsprice[1]
                                error = false}
                    "BNB" ->  { amount_converted =  amount / itemsprice[2]
                                error = false}
                    else-> {amount_textField.error = getString(R.string.astr_amount_error)
                            error = true}
                }

            }

            if (!error) {
                amount_textField.error = null

                    if (amount_currency) {
                    amount_textField.prefixText = "$"
                    amount_textField.suffixText = null
                } else {
                    amount_textField.prefixText = null
                    amount_textField.suffixText = crypto
                }

                amount_currency = !amount_currency

                if (!amount_currency) {
                    var text = amount_value.text.toString() + crypto
                    amount_textField.helperText = text
                } else {
                    var text = "$" + amount_value.text.toString()
                    amount_textField.helperText = text
                }

                amount_value.setText(amount_converted.toString())
            }

        }

        //Cancel button redirects to dashboardFragment
        cance_button.setOnClickListener {
            val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit()
        }

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
         * @return A new instance of fragment SendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SendFragment().apply {
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