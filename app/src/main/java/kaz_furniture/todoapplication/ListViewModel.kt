package kaz_furniture.todoapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kaz_furniture.todoapplication.ToDoApplication.Companion.applicationContext

class ListViewModel : ViewModel() {
    val items = MutableLiveData<Boolean>()
    val dataStore: SharedPreferences = applicationContext.getSharedPreferences("DataStore", Context.MODE_PRIVATE)
    var sortData = dataStore.getInt("KEY",0)
    var sortByInt = MutableLiveData<Int>(sortData)

    fun saveSetting(int: Int) {
        val editor = dataStore.edit()
        editor.putInt("KEY", int)
        editor.apply()
    }

    fun updateData() {
        items.postValue(true)
        items.postValue(false)
    }
}