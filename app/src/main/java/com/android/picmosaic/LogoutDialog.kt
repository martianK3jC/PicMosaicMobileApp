package com.android.picmosaic

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast

class LogoutDialog(context: Context) : Dialog(context) {
    private var onLogoutConfirmed: (() -> Unit)? = null

    fun setOnLogoutConfirmedListener(listener: () -> Unit) {
        onLogoutConfirmed = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.logout_dialog)
        setCancelable(true) // Allow dismissing by clicking outside

        findViewById<Button>(R.id.no_button).setOnClickListener {
            dismiss()
        }

        findViewById<Button>(R.id.yes_button).setOnClickListener {
            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()
            onLogoutConfirmed?.invoke()
            dismiss()
        }
    }
}
