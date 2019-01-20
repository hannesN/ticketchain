package io.ticketchain.wallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.ticketchain.wallet.R
import net.glxn.qrgen.android.QRCode

class ShowAddressFragment() : AbstractFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.show_address, container, false)

        val address = getWallet().getValidAdress()
        val bitmap = QRCode.from(address).withSize(500, 500).bitmap();

        view.findViewById<ImageView>(R.id.qrCurrentAddressImageView).setImageBitmap(bitmap)

        view.findViewById<TextView>(R.id.currentAddressText).text = address


        return view
    }
}