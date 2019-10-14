package io.tennis.statistic


import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import androidx.multidex.MultiDexApplication


class TennisApplication : MultiDexApplication() {

    override  fun onCreate() {
        super.onCreate()
        // Initialize logging. The `DiskLogAdapter` will write to `/sdcard/logger` if enabled
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.addLogAdapter(DiskLogAdapter())
    }
}
