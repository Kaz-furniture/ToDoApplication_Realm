package kaz_furniture.todoapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import kaz_furniture.todoapplication.databinding.ListItemBinding
import java.util.*
import kotlin.collections.ArrayList

class ToDoListAdapter (
    private val layoutInflater: LayoutInflater,
    private val toDoList: ArrayList<ListObject>
) :RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {
    override fun getItemCount() = toDoList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemBinding>(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(toDoList[position])
    }

    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

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
            findById(id)?.also {
                insertOrUpdate(it.apply {
                    deletedAt = Date()
                })
            }
        }

        fun bind(listObject: ListObject) {
            binding.title.text = listObject.title
            binding.deadLine.text = listObject.deadLine
            binding.createdTime.text = listObject.createdTime.toString()
            binding.memo.text = listObject.memo
            binding.more.setOnClickListener {
                PopupMenu(itemView.context, it).also {  popupMenu ->
                    popupMenu.menuInflater.inflate(
                        R.menu.menu_button,
                        popupMenu.menu
                    )
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when(menuItem.itemId) {
                            R.id.menu_button -> delete(listObject.id)
                        }
                        return@setOnMenuItemClickListener true
                    }
                }.show()
            }
        }

    }
}