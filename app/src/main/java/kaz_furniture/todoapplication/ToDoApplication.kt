package kaz_furniture.todoapplication

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class ToDoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ToDoApplication.applicationContext = applicationContext
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

    companion object {
        lateinit var applicationContext: Context
    }
}
