package kaz_furniture.todoapplication.editInfo

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import kaz_furniture.todoapplication.ListObject
import kaz_furniture.todoapplication.R
import kaz_furniture.todoapplication.databinding.FragmentEditBinding
import timber.log.Timber
import java.util.*

class EditFragment : Fragment(R.layout.fragment_edit) {
    companion object{
        val TAG = EditFragment::class.java.simpleName
        private var listObject: ListObject = ListObject()
        private const val KEY_FRAGMENT = "keyFragment"

        fun newInstance(listObject: ListObject) : EditFragment {
            val args = Bundle().apply {
                putSerializable(KEY_FRAGMENT, listObject)
            }
            return EditFragment().apply {
                arguments = args
            }
        }
    }

    interface Callback {
        fun editCompleted()
    }
    private lateinit var title: String
    private lateinit var deadLine: CharSequence
    private lateinit var memo:String

    private var binding : FragmentEditBinding? = null

    private var callback: Callback? = null

    private val viewModel: EditViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().also {
            listObject = (it.getSerializable(KEY_FRAGMENT) as? ListObject) ?:ListObject()
            Timber.d("listObject:${listObject}")
            Timber.d("listObject.title:${listObject.title}")
        }
        title = listObject.title
        deadLine = android.text.format.DateFormat.format(getString(R.string.date_format3), listObject.deadLine)
        memo = listObject.memo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bindingData : FragmentEditBinding? = DataBindingUtil.bind(view)
        binding = bindingData ?:return
        binding?.viewModel = viewModel
        binding?.exTitle?.text = title
        binding?.exMemo?.text = memo
        binding?.dateButton?.setOnClickListener {
            MaterialDialog(this.requireContext()).show {
                dateTimePicker(requireFutureDateTime = true)  { _, dateTime ->
                    deadLine = android.text.format.DateFormat.format(getString(R.string.date_format3), dateTime.time)
                    viewModel.reDeadTime = dateTime.time
                    binding?.dateDisplay?.text = deadLine
                }
            }
        }
        binding?.dateDisplay?.text = deadLine
        viewModel.isEditing.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                callback?.editCompleted()
                Toast.makeText(requireContext(),"編集できました", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
        if (context is Callback) {
            callback = context
        }
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