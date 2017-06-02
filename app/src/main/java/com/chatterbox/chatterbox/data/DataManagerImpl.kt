package com.chatterbox.chatterbox.data

import javax.inject.Singleton

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Implementation of [DataManager], Used to implement methods from all other interfaces
 * in the Model layer. Note that this simply interacts with the given interface methods and
 * DOES not access the classes themselves
 * This is a singleton which means that there will only be 1 instance of this class
 */

@Singleton
class DataManagerImpl : DataManager{
}