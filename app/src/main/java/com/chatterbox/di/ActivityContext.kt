package com.chatterbox.di

import javax.inject.Qualifier

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Used to qualify contexts removing ambiguity between Activity contexts and Application
 * contexts
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext