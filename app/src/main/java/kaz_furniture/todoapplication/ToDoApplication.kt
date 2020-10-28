package kaz_furniture.todoapplication

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        initialize()
    }

    private fun initialize() {
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}
