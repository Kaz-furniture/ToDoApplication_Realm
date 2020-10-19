package kaz_furniture.todoapplication.addInfo

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import kaz_furniture.todoapplication.R
import kaz_furniture.todoapplication.databinding.FragmentAddBinding

class AddFragment : Fragment(R.layout.fragment_add) {
    companion object {
        fun newInstance() : AddFragment {
            return AddFragment()
        }
    }

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