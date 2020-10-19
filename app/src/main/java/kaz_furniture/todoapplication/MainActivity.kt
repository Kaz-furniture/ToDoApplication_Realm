package kaz_furniture.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = ListFragment()
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    fragment,
                    ListFragment::class.java.simpleName
                )
                .commit()
        }

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .build()
        Realm.setDefaultConfiguration(config)
    }

    override fun onResume() {
        super.onResume()
//        Realm.init(this)
//        val realm= Realm.getDefaultInstance()
//        val query = realm.where(ListObject::class.java)
//        val result = query.findAll()
    }
}