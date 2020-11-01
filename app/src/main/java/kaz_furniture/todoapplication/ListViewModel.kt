package kaz_furniture.todoapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    val items = MutableLiveData<Boolean>()

    fun updateData() {
        items.postValue(true)
        items.postValue(false)
    }
}