package com.example.wallet2

import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.os.Handler
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
    private lateinit var amount_value: EditText
    private lateinit var qrImage: ImageView
    private lateinit var assetText: AutoCompleteTextView

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_receive, container, false)

        val textInputLayout = view.findViewById<TextInputLayout>(R.id.asset_transaction)
        assetText = view.findViewById(R.id.assetText_transaction)
        val qrgenerate_button = view.findViewById<MaterialButton>(R.id.qrGenerateBtn)
        val copy_button = view.findViewById<MaterialButton>(R.id.astr_transaction_copy_btn)
        val share_button = view.findViewById<MaterialButton>(R.id.astr_transaction_share_btn)
        val download_button = view.findViewById<MaterialButton>(R.id.astr_transaction_download_btn)
        amount_value = view.findViewById(R.id.amountToSend_value)
        qrImage = view.findViewById(R.id.imageView2)


        val adapter = SpinnerAdapter(requireContext(), items,getAssets_Short())
        (textInputLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //toolbar
        //(activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.app_bar))
        toolbar = view.findViewById(R.id.app_bar)
        toolbar.setNavigationOnClickListener {
            val dashboardFragment = DashboardFragment()

            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right)
            transaction.replace(R.id.fragment_container, dashboardFragment)
            transaction.commit() }

        // Generate QR Code
        qrgenerate_button.setOnClickListener{
            if(amount_value.text.toString().isEmpty() || assetText.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all required information.\n" +
                        "Amount / Asset field is empty", Toast.LENGTH_LONG).show()
            }else{
                createQR()
                qrgenerate_button.isVisible = false
                copy_button.isVisible = true
                share_button.isVisible = true
                download_button.isVisible = true
                amount_value.isEnabled = false
                textInputLayout.isEnabled = false
            }
        }

        // Download the QR Code
        download_button.setOnClickListener{
            downloadQR()
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

    private fun createQR(){
        val text = amount_value.text.toString() + '\n' + assetText.text.toString() + '\n' + "DesarrolloMovil@bedu.org"
        if (text.isNotBlank()){
            val codeWriter = MultiFormatWriter()
            try{
                val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
                for(x in 0 until width){
                    for (y in 0 until height){
                        bitmap.setPixel(x, y, if(bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                qrImage.setImageBitmap(bitmap)
                Toast.makeText(requireContext(), "QR code has been created successfully", Toast.LENGTH_LONG).show()
            } catch (e: WriterException) {
                Toast.makeText(requireContext(), "Error writing the QR code", Toast.LENGTH_LONG).show()
            }
        }
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

}