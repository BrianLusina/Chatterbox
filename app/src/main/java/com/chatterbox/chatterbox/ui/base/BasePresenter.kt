package com.chatterbox.chatterbox.ui.base

/**
 * @author lusinabrian on 02/06/17.
 * @Notes All presenters will inherit from this presenter interface
 */
interface BasePresenter<in V: BaseView>{

    fun onAttach(mBaseView: V)

    fun onDetach()
}