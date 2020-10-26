package kaz_furniture.todoapplication.editInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import kaz_furniture.todoapplication.ListObject
import java.util.*

class EditViewModel : ViewModel() {

    val reTitle = MutableLiveData<String>().apply {
        value = ""
    }

    val reDeadTime = MutableLiveData<String>().apply {
        value = ""
    }

    val reMemo = MutableLiveData<String>().apply {
        value = ""
    }

    fun reCreate(id: String?) {
        findById(id)?.also {
            insertOrUpdate(it.apply {
                val reTitleSnapshot = reTitle.value ?:return
                if (reTitleSnapshot.isNotBlank()) {
                    title = reTitleSnapshot
                }
                val reDeadTimeSnapshot = reDeadTime.value ?:return
                if (reDeadTimeSnapshot.isNotBlank()) {
                    deadLine = reDeadTimeSnapshot
                }
                val reMemoSnapshot = reMemo.value ?:return
                if (reMemoSnapshot.isNotBlank()) {
                    memo = reMemoSnapshot
                }
            })
        }
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