package com.chatterbox.chatterbox.di

import javax.inject.Scope

/**
 * @author lusinabrian on 02/06/17.
 * @Notes Scope to use a specified resource. In this case used in activities
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity
