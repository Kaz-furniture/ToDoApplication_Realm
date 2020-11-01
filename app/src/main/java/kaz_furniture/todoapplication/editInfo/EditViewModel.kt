package kaz_furniture.todoapplication.editInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import kaz_furniture.todoapplication.ListObject
import java.time.LocalDate
import java.util.*

class EditViewModel : ViewModel() {

    val isEditing = MutableLiveData<Boolean>()

    val reTitle = MutableLiveData<String>().apply {
        value = ""
    }

    var reDeadTime: Date = Date()

    val reMemo = MutableLiveData<String>().apply {
        value = ""
    }

    fun reCreate(id: String?) {
        isEditing.postValue(true)
        findById(id)?.also {
            insertOrUpdate(it.apply {
                val reTitleSnapshot = reTitle.value ?:return
                if (reTitleSnapshot.isNotBlank()) {
                    title = reTitleSnapshot
                }
                deadLine = reDeadTime ?:return
                val reMemoSnapshot = reMemo.value ?:return
                if (reMemoSnapshot.isNotBlank()) {
                    memo = reMemoSnapshot
                }
            })
        }
        isEditing.postValue(false)
    }

    private fun insertOrUpdate(data: ListObject) {
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(data)
        }
    }

    private fun findById(id: String?): ListObject? =
        Realm.getDefaultInstance().use { realm->
            realm.where(ListObject::class.java)
                .equalTo(ListObject::id.name, id)
                .isNull(ListObject::deletedAt.name)
                .findFirst()
                ?.let { realm.copyFromRealm(it) }
        }
}