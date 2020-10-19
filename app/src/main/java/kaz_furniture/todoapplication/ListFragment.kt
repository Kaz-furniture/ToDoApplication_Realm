package kaz_furniture.todoapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.realm.Realm
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.RealmResults
import kaz_furniture.todoapplication.addInfo.AddActivity
import kaz_furniture.todoapplication.databinding.FragmentMainBinding
import kaz_furniture.todoapplication.databinding.FragmentToDoListBinding

class ListFragment : Fragment(R.layout.fragment_to_do_list) {
    private lateinit var realm: Realm
    private var binding : FragmentToDoListBinding? = null

    private lateinit var adapter : ToDoListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private val toDoList = ArrayList<ListObject>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realm = Realm.getDefaultInstance()


        val fetchedList = read()
        toDoList.addAll(fetchedList)

        adapter = ToDoListAdapter(layoutInflater, toDoList)
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
        bindingData.fab.setOnClickListener{
            launchAddActivity()
        }

    }

    fun read(): List<ListObject> {
//        val realm = Realm.getDefaultInstance()
        return realm.where(ListObject::class.java).findAll()
    }

    private fun launchAddActivity() {
        val intent = AddActivity.newIntent(requireContext())
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        realm.close()
//        binding?.unbind()
    }
}