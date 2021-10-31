package com.example.wallet2.ui.receive

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.wallet2.Asset_Short
import com.example.wallet2.R
import com.example.wallet2.SpinnerAdapter
import com.example.wallet2.data.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.wallet2.CryptoTransfersApplication
import com.example.wallet2.databinding.FragmentReceiveBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReceiveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
const val CHANNEL_RECEIVE = "CHANNER_RECEIVE"
class ReceiveFragment : Fragment() {
    private lateinit var viewModel: CrytoTransferViewModel
    private lateinit var binding: FragmentReceiveBinding

    val items= listOf("BTC", "ETH", "BNB")

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

//    private lateinit var toolbar: Toolbar
//    private lateinit var amount_value: EditText
//    private lateinit var qrImage: ImageView
//    private lateinit var assetText: AutoCompleteTextView

//    private var viewModel: CrytoTransferViewModel? = null

    private val width = 1000
    private val height = 1000
    private val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNoticationChannelQR()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_receive,
            container,
            false
        )

        viewModel = CrytoTransferViewModel(
            (requireContext().applicationContext as CryptoTransfersApplication).cryptoTransactionRepository
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        val receivedTranRepository = ReceivedTranRepository(ReceivedTranDb.getDatabase(requireContext()).receivedTranDao())
//        viewModel = CrytoTransferViewModel(receivedTranRepository)

//        viewModel = ViewModelProviders.of(this).get(CrytoTransferViewModel::class.java)
//        var dataBaseInstance = ReceivedTranDb.getDatabase(requireContext())
//        viewModel?.setInstanceOfDb(dataBaseInstance)


//        val textInputLayout = view.findViewById<TextInputLayout>(R.id.asset_transaction)
//        assetText = view.findViewById(R.id.assetText_transaction)
//        val qrgenerate_button = view.findViewById<MaterialButton>(R.id.qrGenerateBtn)
//        val copy_button = view.findViewById<MaterialButton>(R.id.astr_transaction_copy_btn)
//        val share_button = view.findViewById<MaterialButton>(R.id.astr_transaction_share_btn)
//        val download_button = view.findViewById<MaterialButton>(R.id.astr_transaction_download_btn)
//        amount_value = view.findViewById(R.id.amountToSend_value)
//        qrImage = view.findViewById(R.id.imageView2)


        val adapter = SpinnerAdapter(requireContext(), items,getAssets_Short())
        (binding.assetTransaction.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //toolbar
        //(activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))
//        toolbar = view.findViewById(R.id.app_bar)
        binding.appBar.setNavigationOnClickListener {
            /*val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right)
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit()*/
            findNavController().navigate(R.id.action_receiveFragment_to_dashboardFragment, null)}

        // Generate QR Code
        binding.qrGenerateBtn.setOnClickListener{
            if(binding.amountToSendValue.text.toString().isEmpty() || binding.assetTextTransaction.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all required information.\n" +
                        "Amount / Asset field is empty", Toast.LENGTH_LONG).show()
            }else{
                createQR(binding.amountToSendValue.text.toString(), binding.assetTextTransaction.text.toString())
                binding.qrGenerateBtn.isVisible = false
                binding.astrTransactionCopyBtn.isVisible = true
                binding.astrTransactionShareBtn.isVisible = true
                binding.astrTransactionDownloadBtn.isVisible = true
                binding.amountToSendValue.isEnabled = false
                binding.assetTextTransaction.isEnabled = false
//                saveObject()
                viewModel.newCryptoTransfer()
                QrGeneratedNotification()
            }
        }

        // Download the QR Code
        binding.astrTransactionDownloadBtn.setOnClickListener{
            downloadQR()
        }

        binding.astrTransactionShareBtn.setOnClickListener{
            shareImage()
        }

        binding.astrTransactionCopyBtn.setOnClickListener{
            getClipboard(requireContext())
        }

        // Inflate the layout for this fragment
        return binding.root
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

    fun saveObject() {
//        val date = getCurrentDateTime()
//        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
//        val receivedTranInstance = ReceivedTran(
//            0,
//            amount_value.text.toString().toFloat(),
//            1,
//            assetText.text.toString(),
//            dateInString,
//            "",
//            "generated"
//        )
//        viewModel?.saveReceivedTran(receivedTranInstance)
//        viewModel?.saveDataIntoDb(receivedTranInstance)
    }

    // Method to share image to applications
    fun shareImage() {
        val drawable = binding.imageView2.drawable as BitmapDrawable
        val bitmap = drawable.bitmap as Bitmap
        val bitmapPath = MediaStore.Images.Media.insertImage(context?.contentResolver, bitmap, "title", null) as String

        val uri = Uri.parse(bitmapPath)

        val intent = Intent(Intent.ACTION_SEND)
        // type of intent
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, "Transaction")
        startActivity(Intent.createChooser(intent, "Share to"))
    }

    // function to copy filename
    fun getClipboard(context: Context) {
        val drawable = binding.imageView2.drawable as BitmapDrawable
        val bitmap = drawable.bitmap as Bitmap
        val bitmapPath = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "title", null) as String
        val uri = Uri.parse(bitmapPath)
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", "QR_wallet.jpg")
//        val clip: ClipData = ClipData.newUri(context.contentResolver, "image", uri)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Filename copied to clipboard", Toast.LENGTH_SHORT).show()
    }



    fun createQR(amount: String, asset:String): Boolean {
        val text = amount + '\n' + asset + '\n' + "DesarrolloMovil@bedu.org"
        if (text.isNotBlank()){
            val codeWriter = MultiFormatWriter()
            try{
                val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
                for(x in 0 until width){
                    for (y in 0 until height){
                        bitmap.setPixel(x, y, if(bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                binding.imageView2.setImageBitmap(bitmap)
                Toast.makeText(requireContext(), "QR code has been created successfully", Toast.LENGTH_LONG).show()
                return true
            } catch (e: WriterException) {
                Toast.makeText(requireContext(), "Error writing the QR code", Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true
    }

    private fun downloadQR(){
        // Download the QR code in Gallery
        try {
            val filename = "QR_wallet.jpg"
            val file = File(requireContext().externalMediaDirs.first(), filename)
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
            Toast.makeText(requireContext(), "Image saved in gallery!: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error saving the image in gallery", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    // Method to register notifications related to QR generator
    private fun setNoticationChannelQR() {
        val name = getString(R.string.qr_notifications)
        val descriptionText = getString(R.string.qr_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_RECEIVE, name, importance).apply {
            description = descriptionText
        }

        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        notificationManager.createNotificationChannel(channel)
    }

    private fun QrGeneratedNotification(){
        // Method to notify the user that has generated a new qr code
        var notification = NotificationCompat.Builder(requireContext(), CHANNEL_RECEIVE)
            .setSmallIcon(R.drawable.astr_logo)
            .setColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.qr_generator_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(requireContext()).run {
            // ids para saber que notificacion es
            notify(20, notification)
        }
    }

}
