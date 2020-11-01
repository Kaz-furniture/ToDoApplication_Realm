package kaz_furniture.todoapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import kaz_furniture.todoapplication.ToDoApplication.Companion.applicationContext
import kaz_furniture.todoapplication.databinding.ListItemBinding
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
            callback?.requestUpdate()
//            callback?.loadListNext()
        }

        private fun finished(id: String) {
            findById(id)?.also {
                insertOrUpdate(it.apply {
                    finished = true
                })
            }
            callback?.requestUpdate()
        }

        private fun reDo(id: String) {
            findById(id)?.also {
                insertOrUpdate(it.apply {
                    finished = false
                })
            }
            callback?.requestUpdate()
        }

        fun View.visibleOrGone(iaVisible: Boolean) {
            visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        fun bind(listObject: ListObject) {
            binding.title.text = listObject.title
            binding.deadLine.text = android.text.format.DateFormat.format(applicationContext.getString(R.string.date_format2), listObject.deadLine)
            binding.createdTime.text = android.text.format.DateFormat.format(applicationContext.getString(R.string.date_format1), listObject.createdTime)
            binding.memo.text = listObject.memo
            binding.finished.isVisible= listObject.finished
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