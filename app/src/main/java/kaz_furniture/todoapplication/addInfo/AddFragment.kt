package kaz_furniture.todoapplication.addInfo

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kaz_furniture.todoapplication.R
import kaz_furniture.todoapplication.databinding.FragmentAddBinding

class AddFragment : Fragment(R.layout.fragment_add) {

    private val viewModel: AddViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    private var binding: FragmentAddBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bindingData: FragmentAddBinding? = DataBindingUtil.bind(view)
        binding = bindingData ?: return
        bindingData.lifecycleOwner = viewLifecycleOwner
        binding?.viewModel = viewModel
        binding?.saveButton?.setOnClickListener {
            viewModel.createObject()
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

    companion object {
        fun newInstance() : AddFragment {
            return AddFragment()
        }
    }
}