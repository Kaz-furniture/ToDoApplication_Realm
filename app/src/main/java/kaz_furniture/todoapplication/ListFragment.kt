package kaz_furniture.todoapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.realm.Realm
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kaz_furniture.todoapplication.ToDoApplication.Companion.applicationContext
import kaz_furniture.todoapplication.addInfo.AddActivity
import kaz_furniture.todoapplication.databinding.FragmentToDoListBinding
import kaz_furniture.todoapplication.editInfo.EditActivity

class ListFragment : Fragment(R.layout.fragment_to_do_list), ToDoListAdapter.Callback {
    private var binding : FragmentToDoListBinding? = null

    private lateinit var adapter : ToDoListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private val toDoList = ArrayList<ListObject>()

    lateinit var mAdView : AdView
    companion object {
        private const val REQUEST_CODE_ADD = 1000
        private const val REQUEST_CODE_EDIT = 1001
        fun create(): ListFragment {
            return ListFragment()
        }
    }

    private val viewModel: ListViewModel by activityViewModels()

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
        val bindingData : FragmentToDoListBinding? = DataBindingUtil.bind(view)

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
        viewModel.items.observe(viewLifecycleOwner, Observer {
            toDoList.clear()
            loadList(toDoList)
        })

    }

    override fun loadListNext() {
        toDoList.clear()
        loadList(toDoList)
    }

    override fun requestUpdate() {
        viewModel.updateData()
    }

    override fun openEdit(listObject: ListObject) {
        val intent = EditActivity.newIntent(requireContext(), listObject)
        startActivityForResult(intent, REQUEST_CODE_EDIT)
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
            viewModel.updateData()
        }
        if (requestCode == REQUEST_CODE_EDIT) {
            viewModel.updateData()
        }
    }

}