package kaz_furniture.todoapplication

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kaz_furniture.todoapplication.databinding.FragmentSettingBinding

class SettingFragment: Fragment(R.layout.fragment_setting) {

    private val viewModel: ListViewModel by activityViewModels()
    private var binding: FragmentSettingBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bindingData : FragmentSettingBinding? = DataBindingUtil.bind(view)

        binding = bindingData ?:return
        binding?.imageView1?.isVisible = true
        binding?.imageView2?.isVisible = false
        binding?.imageView3?.isVisible = false
        binding?.imageView4?.isVisible = false

        binding?.sortCreatedButton?.setOnClickListener {
            viewModel.sortByInt.postValue(0)
        }
        binding?.sortRecreatedButton?.setOnClickListener {
            viewModel.sortByInt.postValue(1)
        }
        binding?.sortDeadButton?.setOnClickListener {
            viewModel.sortByInt.postValue(2)
        }
        binding?.sortRedeadButton?.setOnClickListener {
            viewModel.sortByInt.postValue(3)
        }
        viewModel.sortByInt.observe(viewLifecycleOwner, Observer {
            binding?.imageView1?.isVisible = false
            binding?.imageView2?.isVisible = false
            binding?.imageView3?.isVisible = false
            binding?.imageView4?.isVisible = false
            when (it) {
                0 -> {binding?.imageView1?.isVisible = true}
                1 -> {binding?.imageView2?.isVisible = true}
                2 -> {binding?.imageView3?.isVisible = true}
                3 -> {binding?.imageView4?.isVisible = true}
            }
        })
    }
    fun View.visibleOrGone(iaVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(): SettingFragment {
            return SettingFragment()
        }
    }
}