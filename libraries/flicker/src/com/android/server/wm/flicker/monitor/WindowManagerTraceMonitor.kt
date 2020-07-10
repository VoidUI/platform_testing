/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.android.server.wm.flicker.monitor

import android.os.RemoteException
import android.view.WindowManagerGlobal
import java.nio.file.Path

/** Captures WindowManager trace from WindowManager.  */
open class WindowManagerTraceMonitor @JvmOverloads constructor(
    outputDir: Path? = OUTPUT_DIR
) : TraceMonitor(outputDir ?: OUTPUT_DIR, "wm_trace.pb") {
    private val windowManager= WindowManagerGlobal.getWindowManagerService()

    override fun start() {
        try {
            windowManager.startWindowTrace()
        } catch (e: RemoteException) {
            throw RuntimeException("Could not start trace", e)
        }
    }

    override fun stop() {
        try {
            windowManager.stopWindowTrace()
        } catch (e: RemoteException) {
            throw RuntimeException("Could not stop trace", e)
        }
    }

    override val isEnabled: Boolean
        get() = windowManager.isWindowTraceEnabled
}