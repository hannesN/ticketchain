package io.ticketchain.wallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.ticketchain.wallet.R
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Button
import android.widget.TextView


class SendTicketFragment : AbstractFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.send_ticket, container, false)

        view.findViewById<Button>(R.id.scanBarcodeButton).setOnClickListener {
            val intent = Intent("com.google.zxing.client.android.SCAN")
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
            try {
                startActivityForResult(intent, 0)
            } catch (e:Throwable) {
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                val contents = intent!!.getStringExtra("SCAN_RESULT")

                view!!.findViewById<TextView>(R.id.revieverTextView).text = contents
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}