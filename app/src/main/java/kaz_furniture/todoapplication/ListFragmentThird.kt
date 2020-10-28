package kaz_furniture.todoapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import kaz_furniture.todoapplication.addInfo.AddActivity
import kaz_furniture.todoapplication.databinding.FragmentThirdListBinding
import kaz_furniture.todoapplication.databinding.FragmentToDoListBinding
import kaz_furniture.todoapplication.editInfo.EditActivity

class ListFragmentThird: Fragment(R.layout.fragment_third_list), ToDoListAdapter.Callback {
    private var binding : FragmentThirdListBinding? = null

    private lateinit var adapter : ToDoListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private val toDoList = ArrayList<ListObject>()

    companion object {
        private const val REQUEST_CODE_ADD = 1000
        fun create(): ListFragmentThird {
            return ListFragmentThird()
        }
    }

    private fun loadList(toDoList: ArrayList<ListObject>) {
        binding?.swipeRefresh?.isRefreshing =true
        val fetchedList = read()
        toDoList.addAll(fetchedList)
        adapter.notifyDataSetChanged()
        binding?.swipeRefresh?.isRefreshing =false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ToDoListAdapter(layoutInflater, toDoList, this)

        loadList(toDoList)
        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        val bindingData : FragmentThirdListBinding? = DataBindingUtil.bind(view)

        binding = bindingData ?:return

        bindingData.recyclerView.also {
            it.layoutManager = layoutManager
            it.adapter = adapter
        }
        bindingData.swipeRefresh.setOnRefreshListener {
            toDoList.clear()
            loadList(toDoList)
        }

        bindingData.fab.setOnClickListener{
            launchAddActivity()
        }
    }

    override fun loadListNext() {
        loadList(toDoList)
    }

    override fun openEdit(listObject: ListObject) {
        val intent = EditActivity.newIntent(requireContext(),listObject)
        startActivity(intent)
    }

    private fun read(): List<ListObject> =
        Realm.getDefaultInstance().use { realm->
            realm.where(ListObject::class.java)
                .isNull(ListObject::deletedAt.name)
                .findAll()
                .let { realm.copyFromRealm(it) }
        }

    private fun launchAddActivity() {
        val intent = AddActivity.newIntent(requireContext())
        startActivityForResult(intent, REQUEST_CODE_ADD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD) {
            toDoList.clear()
            loadList(toDoList)
        }
    }

}