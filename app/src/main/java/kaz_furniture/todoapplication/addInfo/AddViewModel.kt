package kaz_furniture.todoapplication.addInfo

import android.app.Application
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    val isFinish = MutableLiveData<Boolean>()

    val bitmap = MutableLiveData<Bitmap>()

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(title) { result.value = submitValidation() }
        result.addSource(deadTime) { result.value = submitValidation() }
        result.addSource(memo) { result.value = submitValidation() }
    }

    private fun submitValidation(): Boolean =
        title.value?.isNotEmpty() == true && deadTime.value?.isNotEmpty() == true && memo.value?.isNotEmpty() == true

    fun createObject() {
        println("createObject start title.value:${title.value} deadTime.value:${deadTime.value} memo.value:${memo.value}" )
        val titleSnapshot = title.value ?: return
        val deadTimeSnapshot = deadTime.value ?:return
        val memoSnapshot = memo.value ?: return
        if (titleSnapshot.isBlank() or deadTimeSnapshot.isBlank()) {
            errorMessage.postValue("タイトルまたは〆切日時が入力されていません")
            return
        }
        Realm.getDefaultInstance().executeTransaction{
            val listObject = it.createObject(ListObject::class.java, UUID.randomUUID().toString())
            listObject.title = titleSnapshot
            listObject.deadLine = deadTimeSnapshot
            listObject.memo = memoSnapshot
            listObject.createdTime = getDate()
            it.copyToRealm(listObject)
        }
        println("createObject end")
        isFinish.postValue(true)
    }
    private fun getDate() : String{
        val date = Date()
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm::ss", Locale.ENGLISH)
        return format.format(date)

    }
}