package kaz_furniture.todoapplication

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
open class ListObject : RealmObject(),Parcelable {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    @Required
    var title = ""
    @Required
    var memo = ""
    @Required
    var deadLine = ""

    var deletedAt : Date? = null

    var createdTime :Date? = null
}