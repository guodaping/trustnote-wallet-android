package org.trustnote.wallet.biz.msgs

import android.view.View
import android.webkit.ValueCallback
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.trustnote.wallet.R
import org.trustnote.wallet.TApp
import org.trustnote.wallet.TTT
import org.trustnote.wallet.biz.FragmentPageBase
import org.trustnote.wallet.biz.MainActivity
import org.trustnote.wallet.biz.wallet.WalletManager
import org.trustnote.wallet.util.AndroidUtils
import org.trustnote.wallet.util.TTTUtils
import org.trustnote.wallet.util.Utils
import org.trustnote.wallet.widget.TMnAmount

class FragmentMsgMyPairId : FragmentPageBase() {

    lateinit var pairIdQR: ImageView
    lateinit var pairIdText: TextView
    lateinit var copyBtn: Button

    override fun getLayoutId(): Int {
        return R.layout.l_dialog_msg_mypairid
    }

    override fun initFragment(view: View) {
        super.initFragment(view)

        pairIdQR = mRootView.findViewById(R.id.qr_code_imageview)
        pairIdText = mRootView.findViewById(R.id.mypairid_text)
        copyBtn = mRootView.findViewById(R.id.mypairid_copy_btn)

        copyBtn.setOnClickListener {

            AndroidUtils.copyTextToClipboard(pairIdText.text.toString())

            Utils.toastMsg(TApp.context.getString(R.string.receive_copy_successful))

        }

        WalletManager.getMyPairId(ValueCallback {
            pairIdText.text = it
            TTTUtils.setupQRCode(it, pairIdQR)
        })

    }

    override fun updateUI() {


    }

}

