package com.example.wallet2.ui.send

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.wallet2.*
import com.example.wallet2.SpinnerAdapter
import com.example.wallet2.data.models.SendTran
import com.example.wallet2.ui.SeedPhraseFragment
import com.example.wallet2.ui.dashboard.DashboardFragment
import com.example.wallet2.ui.user.EMAIL
import com.example.wallet2.ui.user.LoginFragment
import com.example.wallet2.ui.user.PREF_NAME
import com.example.wallet2.ui.user.USERNAME
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.Error
import java.lang.NumberFormatException

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

    //private lateinit var binding: FragmentSendBinding

    val items= listOf("BTC", "ETH", "BNB")
    val itemsprice= listOf(39249.40, 2681.89, 330.14)

    // To get the data
    //private var viewModel: SendViewModel?= null
    private var _asset: String = ""
    private var _amount: Double = 0.0
    private var _address: String = ""
    private var _total: Double = 0.0

    // Notifications Channel ID
    val CHANNEL_SEND = "TRANSACTIONS"

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var assetText: AutoCompleteTextView
    private lateinit var amount_textField: TextInputLayout
    private lateinit var amount_value: EditText
    private lateinit var address_textField: TextInputLayout
    private lateinit var address_value: EditText
    private lateinit var total : TextView
    private lateinit var cance_button: MaterialButton
    private lateinit var next_button: MaterialButton
    private lateinit var qrscan_button: MaterialButton

    private lateinit var header: View
    private lateinit var navigation_view: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var usernameAppbar: TextView
    private lateinit var emailAppbar: TextView
    private lateinit var preferences: SharedPreferences

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
        preferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_send, container, false)

        /*viewModel = ViewModelProviders.of(this).get(SendViewModel::class.java)
        var dataBaseInstance = SendDb.getInstance(requireContext())
        viewModel?.setInstanceOfDb(dataBaseInstance)*/

        drawerLayout = view.findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = view.findViewById(R.id.app_bar) as Toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        ActionBarDrawerToggle(view.context as Activity?,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
        navigation_view = view.findViewById(R.id.nav_view)
        header = navigation_view.getHeaderView(0)
        usernameAppbar = header.findViewById(R.id.userNameAppbar)
        emailAppbar = header.findViewById(R.id.emailAppbar)

        usernameAppbar.text = preferences.getString(USERNAME, "")
        emailAppbar.text = preferences.getString(EMAIL, "")

        val mAuth = FirebaseAuth.getInstance()

        navigation_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_seed -> { val SeedPhraseFragment = SeedPhraseFragment()

                    val fragmentManager = parentFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right)
                    transaction.replace(R.id.fragment_container, SeedPhraseFragment)
                    transaction.commit()
                }

                R.id.logout -> {
                    mAuth!!.signOut()

                    val fragmentManager = parentFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right)
                    transaction.replace(R.id.fragment_container, LoginFragment())
                    transaction.commit()
                }
            }
            true
        }

        textInputLayout = view.findViewById(R.id.asset_send)
        assetText = view.findViewById(R.id.assetText)
        amount_value = view.findViewById(R.id.amount_value)            //--------EdiText
        amount_textField = view.findViewById(R.id.amount_textField)
        address_textField = view.findViewById(R.id.address_input_Layout)
        address_value = view.findViewById(R.id.address_editText)
        total = view.findViewById(R.id.textView11)                     //---------TextView
        cance_button = view.findViewById(R.id.button2)
        next_button = view.findViewById(R.id.button5)
        qrscan_button = view.findViewById(R.id.qrScanBtn)

        val adapter = SpinnerAdapter(requireContext(), items,getAssets_Short())
        (textInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        // NOTIFICATIONS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }

        qrscan_button.setOnClickListener{
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Import data from a QR code")
            builder.setMessage("Choose an option to scan the QR code")
            // Read QR from camera
            builder.setPositiveButton("Camera", { dialogInterface: DialogInterface, i: Int ->
                setUpQRCode()
            })
            //Read QR from gallery
            builder.setNegativeButton("Gallery", { dialogInterface: DialogInterface, i: Int ->
                readImage()
            })
            builder.show()
        }

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

                                    _total = amount_converted.plus(fee)
                                    total.text = _total.toString()
                                }


                                return@OnKeyListener true
                            }

                    false
                }
                false
        })

        address_value.setOnFocusChangeListener { v, hasFocus ->

                if(hasFocus) {
                    address_textField.helperText = "Paste the address and double check it's correct"

                } else {

                    val textamount = address_value.text

                    if (textamount.isNotEmpty() && textamount.length > 2) {

                        val address = textamount.toString()
                        val crypto = assetText.text.toString()

                        if (crypto == "BTC") {

                            when {
                                address.startsWith("1") -> address_textField.helperText =
                                    "P2PKH address entered"
                                address.startsWith("3") -> address_textField.helperText =
                                    "P2SH address entered"
                                address.startsWith("bc1") -> address_textField.helperText =
                                    "Bech32 address entered"
                                else -> address_textField.error = "This address should begin 1, 3 or bc1"
                            }

                        } else {
                            if (address.startsWith("0x")) address_textField.helperText =
                                "hex address entered"
                            else address_textField.error = "This address should begin 0x"

                        }
                    }
                    else address_textField.error = "Address too short or empty"
                }
        }


        //toolbar
//        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))

        //Logic to convert value entered in amount text field from cripto to fiat and viceversa
        amount_textField.setEndIconOnClickListener{
            try {
                var crypto = assetText.text.toString()
                var amount = amount_value.text.toString().toDouble()
                var amount_converted = 0.0


                when (amount_currency) {

                    true -> when (crypto) {
                        "BTC" -> {
                            amount_converted = itemsprice[0] * amount
                            error = false
                        }
                        "ETH" -> {
                            amount_converted = itemsprice[1] * amount
                            error = false
                        }
                        "BNB" -> {
                            amount_converted = itemsprice[2] * amount
                            error = false
                        }
                        else -> {
                            amount_textField.error = getString(R.string.astr_amount_error)
                            error = true
                        }
                    }

                    false -> when (crypto) {
                        "BTC" -> {
                            amount_converted = amount / itemsprice[0]
                            error = false
                        }
                        "ETH" -> {
                            amount_converted = amount / itemsprice[1]
                            error = false
                        }
                        "BNB" -> {
                            amount_converted = amount / itemsprice[2]
                            error = false
                        }
                        else -> {
                            amount_textField.error = getString(R.string.astr_amount_error)
                            error = true
                        }
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
            }catch (e:Exception){
                Toast.makeText(requireContext(), "Please select an Asset and " +
                        "type an Amount to get the conversion", Toast.LENGTH_LONG).show()
            }
        }

        // Cancel button redirects to dashboardFragment
        cance_button.setOnClickListener {
            goToDashboard()
        }

        // Next button confirm the transaction and redirects to dashboardFragment
        next_button.setOnClickListener {
            try {
                _asset = assetText.text.toString()
                _amount = amount_value.text.toString().toDouble()
                _address = address_value.text.toString()
                if (validateInformation(_asset, _amount, _address)) {
                    openConfirmationDialog(_asset, _amount, _address)
                } else {
                    Toast.makeText(requireContext(), "Please enter all required information.\n" +
                            "Asset / Amount / Address field is empty", Toast.LENGTH_LONG).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Please enter all required information.\n" +
                            "Asset / Amount / Address field is empty", Toast.LENGTH_LONG).show()
            }
        }

        // Inflate the layout for this fragment
        //return binding.root
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

    fun validateInformation(asset: String, amount: Double, address: String): Boolean {
        return asset.isNotEmpty() && amount.toString().isNotEmpty() && address.isNotEmpty()
    }

    private fun goToDashboard(){
        val dashboardFragment = DashboardFragment()

        val fragmentManager = parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right)
        transaction.replace(R.id.fragment_container, dashboardFragment)
        transaction.commit()
    }

    //generamos datos dummy con este método
    private fun getAssets_Short(): MutableList<Asset_Short>{
        var assets:MutableList<Asset_Short> = ArrayList()

        assets.add(Asset_Short("BTC",R.drawable.astr_btc_symbol))
        assets.add(Asset_Short("ETH", R.drawable.astr_eth_symbol))
        assets.add(Asset_Short("BNB",R.drawable.astr_bnb_symbol))

        return assets
    }

    fun openConfirmationDialog(asset:String, amount: Double, address: String){
        // val confirmationDialog = ConfirmationDialog(asset, amount, address).show(parentFragmentManager, "Confirmation Dialog")
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Transfer details:")
        builder.setMessage("Sending $amount $asset to $address. Are you sure?")
        // Accept transaction
        builder.setPositiveButton("Send", { dialogInterface: DialogInterface, i: Int ->
            //saveTransfer()
            goToDashboard()
            loadingTransactionMessage()
            progressNotification()
        })
        // Cancel transaction
        builder.setNegativeButton("Cancel", { dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(requireContext(), "Transfer was canceled", Toast.LENGTH_LONG).show()
        })
        builder.show()
    }

    private fun setNotificationChannel() {
        val name = getString(R.string.channel_courses)
        val descriptionText = getString(R.string.courses_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_SEND, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    private fun progressNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_SEND)
            .setContentTitle(getString(R.string.transactions_title))
            .setContentText(getString(R.string.transaction_description))
            .setSmallIcon(R.drawable.ic_monetization)

        val max = 20
        var progress = 0
        var percentage = 0
        val handler = Handler()

        with(NotificationManagerCompat.from(requireContext())) {
            builder.setProgress(max, progress, true)
            notify(34, builder.build())

            Thread(Runnable {
                while (progress < max) {
                    progress += 1

                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        //e.printStackTrace()
                    }
                        handler.post(Runnable {
                            if (progress == max){
                                builder.setContentText("Transaction completed successfully")
                                builder.setProgress(0,0,false)
                            }else{
                                // Calculate the percentage comple
                                percentage = (progress*100)/max
                                builder.setContentText("$percentage% complete")
                                builder.setProgress(max,progress,true)
                            }
                            notify(34, builder.build())
                        })
                    }

            }).start()


        }
    }

    fun saveTransfer() {
        val transfer = SendTran(
            assetType = _asset,
            totalAmount = _amount,
            emailAddress = _address,
            feeValue = 0.00034f,
            totalTransfer = 400f
        )

        //viewModel?.insertTransfer(transfer)
    }

    fun loadingTransactionMessage(){
        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Processing transaction...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        Handler().postDelayed({progressDialog.dismiss()},3000)
        CoroutineScope(Dispatchers.IO).launch{
            (activity as MainActivity).simpleNotification("Envío hecho", "La transacción se ha hecho con éxito", 21)
        }
        //Toast.makeText(requireContext(), "Transfer was successful", Toast.LENGTH_LONG).show()
    }

    // Scanner settings
    private fun setUpQRCode(){
        IntentIntegrator(requireActivity())
            .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // Selecting the type of code to scan
            .setTorchEnabled(false) // Flash enabled / disabled
            .setBeepEnabled(true)   // Sound activated when scanning
            .setPrompt("Scan the QR Code please")  // Message that appears when scanning
            .initiateScan()
    }

    private fun readImage(){
        val pickIntent = Intent(Intent.ACTION_PICK)
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        //SendFragment().startActivityForResult(pickIntent, 111)
        startActivityForResult(pickIntent, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            111 -> {
                if (data == null || data.data == null) {
                    Toast.makeText(requireContext(), "Data import was canceled", Toast.LENGTH_SHORT).show()
                    return
                }
                val uri: Uri = data.data!!
                try {
                    val inputStream: InputStream? = activity?.contentResolver?.openInputStream(uri)
                    var bitmap = BitmapFactory.decodeStream(inputStream)
                    if (bitmap == null) {
                        Toast.makeText(requireContext(), "Image format is not supported", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val width = bitmap.width
                    val height = bitmap.height
                    val pixels = IntArray(width * height)
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
                    bitmap.recycle()
                    val source = RGBLuminanceSource(width, height, pixels)
                    val bBitmap = BinaryBitmap(HybridBinarizer(source))
                    val reader = MultiFormatReader()
                    try {
                        val result = reader.decode(bBitmap)
                        val output: List<String> = result.getText().split("\n")
                        // We write the scan results in the text fields
                        if (output.size==3) {
                            amount_value.setText(output[0])
                            assetText.setText(output[1])
                            address_value.setText(output[2])
                            when (output[0]){
                                "BTC" -> {address_value.setText("bc1"+output[2])}
                                "ETH" -> {address_value.setText("0x"+output[2])}
                                else -> {address_value.setText("0x"+output[2])}
                            }
                            Toast.makeText(requireContext(),"Data import was successful", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(requireContext(), "QR not valid", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: NotFoundException) {
                        Log.e("TAG", "decode exception", e)
                        Toast.makeText(requireContext(), "Data import was not successful", Toast.LENGTH_SHORT).show()
                        Toast.makeText(requireContext(), "Please verify you are reading a QR code", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: FileNotFoundException) {
                    Log.e("TAG", "can not open file" + uri.toString(), e)
                }
            }
            else -> {
                val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

                if (result != null){
                    if (result.contents == null) {
                        Toast.makeText(requireContext(), "Scan was canceled", Toast.LENGTH_LONG).show()
                    } else {
                        // We write the scan results in the text fields
                        val resultado = result.contents
                        val output: List<String> = resultado.split("\n")
                        if (output.size==3) {
                            amount_value.setText(output[0])
                            assetText.setText(output[1])
                            when (output[0]){
                                "BTC" -> {address_value.setText("bc1"+output[2])}
                                "ETH" -> {address_value.setText("0x"+output[2])}
                                else -> {address_value.setText("0x"+output[2])}
                            }
                            Toast.makeText(requireContext(), "Scan was successful", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "QR not valid", Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }
        }
    }

}