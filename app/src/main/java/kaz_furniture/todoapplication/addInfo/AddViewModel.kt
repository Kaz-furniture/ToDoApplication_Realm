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
    val title = MutableLiveData<String>().apply {
        value=""
    }
    val deadTime = MutableLiveData<String>().apply {
        value=""
    }
    val memo = MutableLiveData<String>()

    val createComplete = MutableLiveData<Boolean>()

    fun createObject() {
        val titleSnapshot = title.value ?:return
        if (titleSnapshot.isBlank()) {
            errorMessage.postValue("タイトルが入力されていません")
            return
        }
        val deadTimeSnapshot = deadTime.value ?:return
        if (deadTimeSnapshot.isBlank()) {
            errorMessage.postValue("〆切日時が入力されていません")
            return
        }
        val memoSnapshot = memo.value ?: "特になし"

        Realm.getDefaultInstance().executeTransaction{
            val listObject =it.createObject(ListObject::class.java, UUID.randomUUID().toString())
            listObject.title = titleSnapshot
            listObject.deadLine = deadTimeSnapshot
            listObject.memo = memoSnapshot
            listObject.createdTime = Date()
            it.copyToRealm(listObject)
        }
        createComplete.postValue(true)
    }

//    private fun completelyDelete(id: String) {
//        Realm.getDefaultInstance().executeTransaction {
//            var toDoObject = it.where(ListObject::class.java).equalTo("id",id).findAll()
//            toDoObject.deleteAllFromRealm()
//        }
//    }



//    private fun getDate() : String{
//        val date = Date()
//        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH)
//        return format.format(date)
//    }


}