package kaz_furniture.todoapplication.addInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import kaz_furniture.todoapplication.ListObject
import kotlinx.coroutines.CoroutineScope
import java.util.*

class AddViewModel(
    private val coroutineScope: CoroutineScope,
    application: Application
) : AndroidViewModel(application) {


}