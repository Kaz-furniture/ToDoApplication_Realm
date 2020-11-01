package kaz_furniture.todoapplication.addInfo

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.google.android.material.snackbar.Snackbar
import kaz_furniture.todoapplication.R
import kaz_furniture.todoapplication.databinding.FragmentAddBinding
import java.util.*

class AddFragment : Fragment(R.layout.fragment_add) {
    companion object {
        fun newInstance() : AddFragment {
            return AddFragment()
        }
    }
    var deadLine: Calendar = Calendar.getInstance()

    private val viewModel: AddViewModel by activityViewModels()


    interface Callback {
        fun createCompleted()
    }

    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)

        if (context is Callback) {
            callback = context
        }
    }

    private var binding: FragmentAddBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bindingData: FragmentAddBinding? = DataBindingUtil.bind(view)
        binding = bindingData ?: return
        bindingData.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        val deadLineSnapshot = android.text.format.DateFormat.format(getString(R.string.date_format3), deadLine.time)
        binding?.dateDisplay?.text = deadLineSnapshot

        viewModel.createComplete.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"登録できました",Toast.LENGTH_LONG).show()
            callback?.createCompleted()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        })
        binding?.dateButton?.setOnClickListener {
            MaterialDialog(this.requireContext()).show {
                dateTimePicker(requireFutureDateTime = true)  { _, dateTime ->
                    deadLine = dateTime
                    binding?.dateDisplay?.text = android.text.format.DateFormat.format(getString(R.string.date_format3), deadLine.time)
                    viewModel.deadTime = deadLine
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add,menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding?.unbind()
    }


}