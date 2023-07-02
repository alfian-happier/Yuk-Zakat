package app.alfian.yukzakat.di

/**
 * Created by Alfian on 6/13/2023.
 */

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

@Scope
@Retention(RUNTIME)
annotation class PerScreen
