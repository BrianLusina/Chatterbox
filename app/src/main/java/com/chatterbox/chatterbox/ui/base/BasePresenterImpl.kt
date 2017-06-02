package com.chatterbox.chatterbox.ui.base

import com.chatterbox.chatterbox.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Presenter implementation, Each implementation of a presenter layer must inherit from
 * this class
 */
abstract class BasePresenterImpl<V : BaseView> @Inject constructor(val mDataManager: DataManager, val mCompositeDisposable: CompositeDisposable): BasePresenter<V>{
    /**
     * Gets the base view
     * @return [BaseView]
     */

    var baseView: V? = null
        private set

    override fun onAttach(mBaseView: V) {
        this.baseView = mBaseView
    }

    override fun onDetach() {
        mCompositeDisposable.dispose()
        baseView = null
    }

    /**
     * Checks if the view has been attached */
    val isViewAttached: Boolean
        get() = baseView != null

    /**
     * Checks if the view has been attached
     * @throws BaseViewNotAttachedException error that is thrown when the view is not attached.
     * *
     */
    fun checkViewAttached() {
        if (!isViewAttached) {
            throw BaseViewNotAttachedException()
        }
    }

    /**
     * Custom runtime exception that is thrown when an a request of data is made to the presenter before
     * attaching the view.
     */
    class BaseViewNotAttachedException : RuntimeException("Please call Presenter.onAttach(BaseView) before requesting data to Presenter")

    companion object {
        private val TAG = BasePresenterImpl::class.java.simpleName
    }
}