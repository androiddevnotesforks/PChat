/*
 * Copyright 2023 PChat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.pchat.core.di

import android.app.Application
import com.peterchege.pchat.BuildConfig
import com.peterchege.pchat.core.crashlytics.CrashlyticsTree
import dagger.hilt.android.HiltAndroidApp
import org.jetbrains.annotations.NotNull
import timber.log.Timber


@HiltAndroidApp
class PChatApp :Application(){
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }
}


private fun initTimber() = when {
    BuildConfig.DEBUG -> {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(@NotNull element: StackTraceElement): String {
                return super.createStackElementTag(element) + ":" + element.lineNumber
            }
        })
    }
    else -> {
        Timber.plant(CrashlyticsTree())
    }
}