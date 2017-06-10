package com.chatterbox.chatterbox.ui.entry.splash

import com.chatterbox.chatterbox.data.DataManager
import com.chatterbox.chatterbox.ui.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 10/06/17.
 * @Notes
 */
class SplashPresenterImpl<V: SplashView> @Inject constructor(mDataManager: DataManager, mCompositeDisposable: CompositeDisposable): BasePresenterImpl<V>(mDataManager, mCompositeDisposable), SplashPresenter<V>{

    override fun onAttach(mBaseView: V) {
        super.onAttach(mBaseView)
    }

    override fun onViewInitialized() {
        decideNextActivity()
    }

    /**
     * Decides which activity to open up first
     * if first start, open introduction activity
     * if second start, open auth activity
     * */
    fun decideNextActivity(){
        if(mDataManager.getFirstStart()){
            mDataManager.setFirstStart(false)
            baseView?.openIntroductionActivity()
        }else{
            baseView?.openAuthActivity()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}