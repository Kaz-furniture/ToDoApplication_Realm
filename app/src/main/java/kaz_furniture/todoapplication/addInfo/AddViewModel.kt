package kaz_furniture.todoapplication.addInfo

import android.app.Application
import androidx.lifecycle.*
import io.realm.Realm
import kaz_furniture.todoapplication.ListObject
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val deadTime = MutableLiveData<String>()
    val memo = MutableLiveData<String>()

    fun createObject() {
        val titleSnapshot = title.value ?: return
        val deadTimeSnapshot = deadTime.value ?:return
        val memoSnapshot = memo.value ?: return
        if (titleSnapshot.isBlank() ) {
            errorMessage.postValue("タイトルまたは〆切日時が入力されていません")
            return
        }

        Realm.getDefaultInstance().executeTransaction{
            val listObject =it.createObject(ListObject::class.java, UUID.randomUUID().toString())
            listObject.title = titleSnapshot
            listObject.deadLine = deadTimeSnapshot
            listObject.memo = memoSnapshot
            listObject.createdTime = getDate()
            it.copyToRealm(listObject)
        }
    }


    private fun getDate() : String{
        val date = Date()
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH)
        return format.format(date)
    }


}