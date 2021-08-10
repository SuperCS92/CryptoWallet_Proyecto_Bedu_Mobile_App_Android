package com.example.wallet2

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class ConfirmationDialog(val asset:String, val amount: Double, val address: String): AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Transfer details:")
            .setMessage(" Sending ${amount} ${asset} to ${address}. Are you sure?")
            .setPositiveButton("Send", DialogInterface.OnClickListener { dialog, which ->

            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, which ->

            })

        return alertDialog.create()
    }
}