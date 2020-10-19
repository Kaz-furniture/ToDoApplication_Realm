package kaz_furniture.todoapplication.addInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import kaz_furniture.todoapplication.ListObject
import kaz_furniture.todoapplication.R
import java.util.*

class AddActivity : AppCompatActivity() {
    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, AddActivity::class.java)
        }
    }
    lateinit var realm: Realm

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

    override fun onResume() {
        super.onResume()
    }

    val errorMessage = MutableLiveData<String>()

    val title = MutableLiveData<String>()
    val deadTime = MutableLiveData<String>()
    val memo = MutableLiveData<String>()

    fun createObject() {
        val titleSnapshot = title.value ?: return
        val deadTimeSnapshot = deadTime.value ?:return
        val memoSnapshot = memo.value ?: return
        if (titleSnapshot.isBlank() or deadTimeSnapshot.isBlank()) {
            errorMessage.postValue("タイトルまたは〆切日時が入力されていません")
            return
        }
        realm.executeTransaction{
            val listObject =realm.createObject(ListObject::class.java, UUID.randomUUID().toString())
            listObject.title = titleSnapshot
            listObject.deadLine = deadTimeSnapshot
            listObject.memo = memoSnapshot
            listObject.createdTime = Date()
            realm.copyToRealm(listObject)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                createObject()
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}