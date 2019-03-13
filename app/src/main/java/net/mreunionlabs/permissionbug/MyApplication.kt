package net.mreunionlabs.permissionbug

import android.app.Application
import android.support.annotation.Nullable
import android.util.Log
import android.util.Log.INFO
import net.mreunionlabs.permissionbug.koin.appModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

//        AndroidThreeTen.init(this)

        // start Koin context
        startKoin(this, listOf(appModule))
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        fun isLoggable(priority: Int, @Nullable tag: String): Boolean {
            return priority >= INFO
        }

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

//            FakeCrashLibrary.log(priority, tag, message)
//
//            if (t != null) {
//                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t)
//                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t)
//                }
//            }
        }
    }
}

