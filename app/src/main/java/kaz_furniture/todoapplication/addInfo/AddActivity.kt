package kaz_furniture.todoapplication.addInfo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import io.realm.RealmConfiguration
import kaz_furniture.todoapplication.ListObject
import kaz_furniture.todoapplication.R
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity(),AddFragment.Callback {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, AddActivity::class.java)
        }
    }

    private val viewModel: AddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        if (savedInstanceState == null) {
            val fragment = AddFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit()
        }
    }

    override fun createCompleted() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                viewModel.createObject()
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Realm.getDefaultInstance().close()
    }

}