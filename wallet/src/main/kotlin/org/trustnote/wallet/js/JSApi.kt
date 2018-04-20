package org.trustnote.wallet.js

import android.webkit.ValueCallback
import org.trustnote.wallet.util.Utils

class JSApi {

    /**
     * 生成助记词
     * @method mnemonic
     * @for Base
     * @param {void}
     * @return {string} 12个助记词
     */
    fun mnemonic(cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("window.Client.mnemonic();", cb)
    }

    fun mnemonicSync(): String {
        return TWebView.sInstance.callJSSync("window.Client.mnemonic();")
    }

    /**
     * 根据助记词生成根私钥
     * @method xPrivKey
     * @for Base
     * @param {string}  助记词
     * @return {string} 私钥
     */
    fun xPrivKey(mnemonic: String, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("window.Client.xPrivKey($mnemonic);", cb)
    }

    fun xPrivKeySync(mnemonic: String): String {
        return TWebView.sInstance.callJSSync("window.Client.xPrivKey($mnemonic);")
    }


    /**
     * 生成根公钥
     * @method xPubKey
     * @for Base
     * @param {string}  根私钥
     * @return {string} 根公钥
     */
    fun xPubKey(xPrivKey: String, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("window.Client.xPubKey($xPrivKey);", cb)
    }

    /**
     * 生成钱包公钥
     * @method walletPubKey
     * @for Base
     * @param {string}  私钥
     * @param {int}     钱包index 0-
     * @return {string} 钱包公钥
     */
    fun walletPubKey(xPrivKey: String, num: Int, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("window.Client.walletPubKey($xPrivKey,$num);", cb)
    }

    fun walletPubKeySync(xPrivKey: String, num: Int): String {
        return TWebView.sInstance.callJSSync("window.Client.walletPubKey($xPrivKey,$num);")
    }

    /**
     * 生成钱包ID
     * @method walletID
     * @for Base
     * @param {string}  钱包公钥
     * @return {string} 钱包ID
     */
    fun walletID(walletPubKey: String, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("""window.Client.walletID($walletPubKey);""", cb)
    }

    fun walletIDSync(walletPubKey: String): String {
        return TWebView.sInstance.callJSSync("""window.Client.walletID($walletPubKey);""")
    }

    /**
     * 生成设备地址
     * @method deviceAddress
     * @for Base
     * @param {string}  根私钥
     * @return {string} 设备地址
     */
    fun deviceAddress(xPrivKey: String, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("""window.Client.deviceAddress($xPrivKey);""", cb)
    }
    fun deviceAddressSync(xPrivKey: String): String {
        return TWebView.sInstance.callJSSync("""window.Client.deviceAddress($xPrivKey);""")
    }

    /**
     * 生成钱包地址对应的公钥
     * @method walletAddress
     * @for Base
     * @param {string}  钱包公钥
     * @param {int}     收款地址为 0; 找零地址为 1;
     * @param {int}     地址index 0-
     * @return {string} 钱包地址对应的公钥
     */
    fun walletAddressPubkey(wallet_xPubKey: String, isChange: Int, idx: Int, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("""window.Client.walletAddressPubkey($wallet_xPubKey,$isChange,$idx);""", cb)
    }

    fun walletAddressPubkeySync(wallet_xPubKey: String, isChange: Int, idx: Int): String {
        return TWebView.sInstance.callJSSync("""window.Client.walletAddressPubkey($wallet_xPubKey,$isChange,$idx);""")
    }
    /**
     * 生成钱包的地址
     * @method walletAddress
     * @for Base
     * @param {string}  钱包公钥
     * @param {int}     收款地址为 0; 找零地址为 1;
     * @param {int}     地址index 0-
     * @return {string} 钱包地址
     */
    fun walletAddress(wallet_xPubKey: String, isChange: Int, idx: Int, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("""window.Client.walletAddress($wallet_xPubKey,$isChange,$idx);""", cb)
    }

    fun walletAddressSync(wallet_xPubKey: String, isChange: Int, idx: Int): String {
        return TWebView.sInstance.callJSSync("""window.Client.walletAddress($wallet_xPubKey,$isChange,$idx);""")
    }

    /**
     * 生成ecdsa签名公钥
     * @method ecdsaPubkey
     * @for Base
     * @param {string}  钱包私钥
     * @param {string}  派生路径
     * @return {string} 签名公钥
     */
    fun ecdsaPubkey(xPrivKey: String, path: String, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("window.Client.ecdsaPubkey($xPrivKey,$path);", cb)
    }

    fun ecdsaPubkeySync(xPrivKey: String, path: String):String {
        return TWebView.sInstance.callJSSync("window.Client.ecdsaPubkey($xPrivKey,$path);")
    }


    /**
     * 签名
     * @method sign
     * @for Base
     * @param {string}  base64编码过的hash
     * @param {string}  根私钥
     * @param {string}  派生路径
     * @return {string} 签名结果
     */
    fun sign(b64_hash: String, xPrivKey: String, path: String, cb: ValueCallback<String>) {
        val seperator = ","

        Utils.debugLog("GUODAPING")
        Utils.debugLog("$b64_hash$seperator$xPrivKey$seperator$path");

        TWebView.sInstance.callJS("window.Client.sign($b64_hash$seperator$xPrivKey$seperator$path);", cb)
    }

    /**
     * 验证签名
     * @method verify
     * @for Base
     * @param {string}  base64编码过的hash
     * @param {string}  签名信息
     * @param {string}  派生公钥
     * @return {bool}   验签结果
     */
    fun verify(b64_hash: String, sig: String, pub_key: String, cb: ValueCallback<String>) {
        val seperator = ","
        TWebView.sInstance.callJS("window.Client.verify($b64_hash$seperator$sig$seperator$pub_key);", cb)
    }

    /**
     * 获得设备消息hash
     * @method getDeviceMessageHashToSign
     * @for Base
     * @param {string}  消息JSON字符串
     * @return {string} base64过的hash
     */
    fun getDeviceMessageHashToSign(unit: String, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("window.Client.getDeviceMessageHashToSign($unit);", cb)
    }

    /**
     * 获得交易单元hash
     * @method getUnitHashToSign
     * @for Base
     * @param {string}  单元JSON字符串
     * @return {string} base64过的hash
     */
    fun getUnitHashToSign(unit: String, cb: ValueCallback<String>) {
        TWebView.sInstance.callJS("window.Client.getUnitHashToSign($unit);", cb)
    }

    fun getUnitHashToSignSync(unit: String): String {
        return TWebView.sInstance.callJSSync("window.Client.getUnitHashToSign($unit);")
    }

}

class JSResult {
    var result: String = ""
}