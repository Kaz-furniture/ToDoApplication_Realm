package kaz_furniture.todoapplication.addInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.realm.Realm
import kaz_furniture.todoapplication.R

class AddActivity : AppCompatActivity() {

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

        viewModel.isFinish.observe(this, Observer {
            if (it)
                finish()
        })
    }

    override fun onResume() {
        super.onResume()
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

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, AddActivity::class.java)
        }
    }
}