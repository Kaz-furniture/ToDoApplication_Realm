package kaz_furniture.todoapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm

class ListViewModel : ViewModel() {

    val items = MutableLiveData<List<ListObject>>()

    fun updateData() {
        items.postValue(Realm.getDefaultInstance().use { realm ->
            realm.where(ListObject::class.java)
                .isNull(ListObject::deletedAt.name)
                .findAll()
                .let { realm.copyFromRealm(it) }
        })
    }
}