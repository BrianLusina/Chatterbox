package com.chatterbox.chatterbox.ui.auth

import com.chatterbox.chatterbox.data.DataManager
import com.chatterbox.chatterbox.ui.base.BasePresenter
import com.chatterbox.chatterbox.ui.base.BasePresenterImpl
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 02/06/17.
 * *
 * @Notes
 */

class AuthPresenterImpl<V : AuthView>
@Inject constructor(mDataManager: DataManager, mCompositeDisposable: CompositeDisposable): BasePresenterImpl<V>(mDataManager, mCompositeDisposable), AuthPresenter<V>{

    override fun onAttach(mBaseView: V) {
        super.onAttach(mBaseView)
    }

    override fun onDetach() {
        super.onDetach()
    }
}
