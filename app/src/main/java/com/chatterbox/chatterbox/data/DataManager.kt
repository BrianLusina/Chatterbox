package com.chatterbox.chatterbox.data

import com.chatterbox.chatterbox.data.api.ApiHelper

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Interface layer that will be accesed by all presenter layers in the application
 * This will be used to delegate a given function to responsible party. e.g.
 * If the preference file access is requested, then the preference data layer will handle the
 * given task and so on
 * This interface will extend all other interfaces and [DataManagerImpl] will handle the
 * implementation
 */
interface DataManager : ApiHelper{
}