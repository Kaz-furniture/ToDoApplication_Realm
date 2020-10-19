package kaz_furniture.todoapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kaz_furniture.todoapplication.databinding.ListItemBinding

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
        fun bind(listObject: ListObject) {
            binding.title.text = listObject.title
            binding.deadLine.text = listObject.deadLine
            binding.memo.text = listObject.memo
        }
    }
}