package kaz_furniture.todoapplication

import androidx.fragment.app.Fragment

interface ResourceStore {
    companion object {
        val tabList = listOf(
            "todo", "all", "finished", "sort"
        )
        val pagerFragments:List<Fragment> = listOf(
            ListFragmentSecond.create(), ListFragment.create(), ListFragmentThird.create(), SettingFragment.create())
    }
}