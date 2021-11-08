package com.example.wallet2

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.wallet2.databinding.FragmentAssetBinding
import com.example.wallet2.ui.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.fragment_transaction.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [TransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionFragment(val position: Int = 0) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var toolbar: Toolbar

    private var amountTransaction = ""
    private var assetTransaction = ""
    private var dateTransaction = ""
    private lateinit var amountText: TextView
    private lateinit var amountTotal: TextView
    private lateinit var transactionCreated: TextView
    private lateinit var transactionSubmitted: TextView
    private lateinit var transactionConfirmed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        // Safe args to get new values
        val safeArgs: TransactionFragmentArgs by navArgs()
        amountTransaction = safeArgs.amountTransaction
        assetTransaction = safeArgs.assetTransaction
        dateTransaction = safeArgs.dateTransaction

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.explode)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        amountText = view.findViewById(R.id.astr_transaction_amount_value)
        amountTotal = view.findViewById(R.id.astr_transaction_total_value)
        transactionCreated = view.findViewById(R.id.astr_transactionCreated)
        transactionSubmitted = view.findViewById(R.id.astr_transactionSubmited)
        transactionConfirmed = view.findViewById(R.id.astr_transactionConfirmed)

        val finalAmount = amountTransaction + " " + assetTransaction
        val fee = amountTransaction.toFloat()*0.0010625

        amountText.text = finalAmount
        amountTotal.text = "${amountTransaction.toFloat() + fee} " + " " + assetTransaction
        transactionCreated.text = "Transaction created with a value of " + finalAmount + " at " + dateTransaction + "."
        transactionSubmitted.text = "Transaction submitted with a gas fee of " + fee + " " + assetTransaction + " at " + dateTransaction + "."
        transactionConfirmed.text = "Transaction confirmed at " + dateTransaction + "."

        toolbar = view.findViewById(R.id.app_bar)
        toolbar.setNavigationOnClickListener {
            /*val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right)
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit()*/
            findNavController().navigate(R.id.action_transactionFragment_to_dashboardFragmentDest, null)}

        //toolbar
       // (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))

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
         * @return A new instance of fragment TransactionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, position: Int) =
            TransactionFragment(position).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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