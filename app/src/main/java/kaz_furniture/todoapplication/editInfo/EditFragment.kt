package kaz_furniture.todoapplication.editInfo

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import kaz_furniture.todoapplication.ListObject
import kaz_furniture.todoapplication.R
import kaz_furniture.todoapplication.databinding.FragmentEditBinding
import kaz_furniture.todoapplication.databinding.FragmentToDoListBinding
import timber.log.Timber

class EditFragment : Fragment(R.layout.fragment_edit) {
    companion object{
        val TAG = EditFragment::class.java.simpleName
        private var listObject :ListObject? = null
        private const val KEYFRAGMENT = "keyFragment"

        fun newInstance(listObject: ListObject) : EditFragment {
            val args = Bundle().apply {
                putSerializable(KEYFRAGMENT, listObject)
            }
            return EditFragment().apply {
                arguments = args
            }
        }
    }
    private lateinit var title: String
    private lateinit var deadLine: String
    private lateinit var memo:String

    private var binding : FragmentEditBinding? = null

    private val viewModel: EditViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().also {
            listObject = (it.getSerializable(KEYFRAGMENT) as? ListObject) ?:ListObject()
            Timber.d("listObject:${listObject}")
            Timber.d("listObject.title:${listObject?.title}")
        }
        title = listObject?.title ?:return
        deadLine = listObject?.deadLine ?:return
        memo = listObject?.memo ?:return
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bindingData : FragmentEditBinding? = DataBindingUtil.bind(view)
        binding = bindingData ?:return
        binding?.viewModel = viewModel
        binding?.exTitle?.text = title
        binding?.exDeadLine?.text = deadLine
        binding?.exMemo?.text = memo
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = listObject?.id
        return when (item.itemId) {
            R.id.menu_save -> {
                viewModel.reCreate(id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}