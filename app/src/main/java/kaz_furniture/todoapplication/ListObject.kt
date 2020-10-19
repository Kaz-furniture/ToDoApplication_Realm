package kaz_furniture.todoapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class ListObject : RealmObject() {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
    @Required
    var title = ""
    @Required
    var memo = ""
    @Required
    var deadLine = ""

    var createdTime : Date = Date()
}