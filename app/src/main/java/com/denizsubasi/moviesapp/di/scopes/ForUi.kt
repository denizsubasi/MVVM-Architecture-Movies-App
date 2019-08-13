package com.denizsubasi.moviesapp.di.scopes

import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ForUi
