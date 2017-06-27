package com.chatterbox.di

import javax.inject.Qualifier

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Specifies that context to be used is the application context
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext
