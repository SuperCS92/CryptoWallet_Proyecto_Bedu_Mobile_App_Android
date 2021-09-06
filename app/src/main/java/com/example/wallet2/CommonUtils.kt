package com.example.wallet2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable

object CommonUtils {
    fun showLoadingDialog(context: Context?): Dialog {
        val progressDialog = Dialog(context!!)

        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.progress_dialog)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)

            return it
        }
    }
}