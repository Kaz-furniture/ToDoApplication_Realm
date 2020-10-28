package kaz_furniture.todoapplication

import androidx.fragment.app.Fragment

interface ResourceStore {
    companion object {
        val tabList = listOf(
            "tab1", "tab2", "tab3"
        )
        val pagerFragments:List<Fragment> = listOf(
            ListFragment.create(), ListFragmentSecond.create(), ListFragmentThird.create())
    }
}