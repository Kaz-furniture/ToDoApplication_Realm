package kaz_furniture.todoapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.io.Serializable
import java.util.*

open class ListObject : RealmObject(),Serializable {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    @Required
    var title = ""
    @Required
    var memo = ""
    @Required
    var deadLine :Date? = null

    var deletedAt : Date? = null

    var createdTime :Date? = null

    var finished :Boolean = false
}