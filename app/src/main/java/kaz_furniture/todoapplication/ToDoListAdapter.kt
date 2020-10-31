package kaz_furniture.todoapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import kaz_furniture.todoapplication.databinding.ListItemBinding
import kaz_furniture.todoapplication.editInfo.EditActivity
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ToDoListAdapter (
    private val layoutInflater: LayoutInflater,
    private val toDoList: ArrayList<ListObject>,
    private val callback: Callback?
) :RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    interface Callback {
        fun loadListNext()
        fun openEdit(listObject: ListObject)
        fun requestUpdate()
    }

    override fun getItemCount() = toDoList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemBinding>(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )
        return ViewHolder(binding,callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(toDoList[position])
    }

    class ViewHolder(
        private val binding: ListItemBinding,
        private val callback: Callback?
    ) : RecyclerView.ViewHolder(binding.root) {

        private val isChanging = MutableLiveData<Boolean>(false)

        private fun insertOrUpdate(data: ListObject) {
            Realm.getDefaultInstance().executeTransaction {
                it.insertOrUpdate(data)
            }
        }

        private fun findById(id: String): ListObject? =
            Realm.getDefaultInstance().use { realm->
                realm.where(ListObject::class.java)
                    .equalTo(ListObject::id.name, id)
                    .isNull(ListObject::deletedAt.name)
                    .findFirst()
                    ?.let { realm.copyFromRealm(it) }
            }

        private fun delete(id: String) {
            isChanging.postValue(true)
            findById(id)?.also {
                insertOrUpdate(it.apply {
                    deletedAt = Date()
                })
            }
            isChanging.postValue(false)
            callback?.requestUpdate()
        }

        private fun finished(id: String) {
            isChanging.postValue(true)
            findById(id)?.also {
                insertOrUpdate(it.apply {
                    finished = true
                })
            }
            isChanging.postValue(false)
        }

        private fun reDo(id: String) {
            isChanging.postValue(true)
            findById(id)?.also {
                insertOrUpdate(it.apply {
                    finished = false
                })
            }
            isChanging.postValue(false)
            callback?.requestUpdate()
        }

        fun bind(listObject: ListObject) {
            binding.title.text = listObject.title
            binding.deadLine.text = listObject.deadLine
            binding.createdTime.text = android.text.format.DateFormat.format("yyyy/mm/dd HH:mm:ss", listObject.createdTime)
            binding.memo.text = listObject.memo
            binding.finished.isEnabled  = listObject.finished
            binding.more.setOnClickListener {
                PopupMenu(itemView.context, it).also {  popupMenu ->
                    popupMenu.menuInflater.inflate(
                        R.menu.menu_button,
                        popupMenu.menu
                    )
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when(menuItem.itemId) {
                            R.id.menu_delete -> delete(listObject.id)
                            R.id.menu_edit -> callback?.openEdit(listObject)
                            R.id.menu_finished -> finished(listObject.id)
                            R.id.menu_reDo -> reDo(listObject.id)
                        }
                        return@setOnMenuItemClickListener true
                    }
                }.show()
            }
        }

    }


}