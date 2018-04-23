package org.trustnote.wallet.network.hubapi

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.trustnote.wallet.TApp
import org.trustnote.wallet.TTT
import org.trustnote.wallet.network.RequestMap
import org.trustnote.wallet.util.Utils
import java.util.concurrent.TimeUnit

class HubSocketModel {

    val mGetWitnessTag = Utils.generateRandomString(30)
    val mHeartbeatTag = Utils.generateRandomString(30)
    val mGetHistoryTag = Utils.generateRandomString(30)
    val mHubAddress = TTT.testHubAddress
    val mRequestMap = RequestMap()
    val mSubject: Subject<HubMsg> = PublishSubject.create()
    lateinit var mHubClient: HubClient
    lateinit var mHeartBeatTask: HeartBeatTask

    fun setupRetryLogic() {
        Observable.interval(60, TimeUnit.SECONDS).observeOn(Schedulers.computation()).subscribe {
            retry()
        }
    }

    @Synchronized
    private fun retry() {
        for ((tag, hubMsg) in mRequestMap.getRetryMap()) {
            if (hubMsg.shouldRetry()) {
                Utils.debugHub("retry with:" + hubMsg.toHubString())
                mHubClient.sendHubMsg(hubMsg)
            }
        }
    }

    //TODO: for future copy.
    private var connectivityDisposable: Disposable? = null

    fun onResume() {
        connectivityDisposable = ReactiveNetwork.observeNetworkConnectivity(TApp.context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { connectivity ->
                    Utils.debugHub(connectivity.toString())
                    val state = connectivity.state
                    val name = connectivity.typeName
                    //TODO:
                    //connectivity_status.text = String.format("state: %s, typeName: %s", state, name)
                }
    }

    fun onPause() {
        safelyDispose(connectivityDisposable)
    }

    private fun safelyDispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun dispose() {
        //TODO: ("not implemented")
    }


}